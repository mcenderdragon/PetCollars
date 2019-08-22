package mcenderdragon.petcollars.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableSet;

import mcenderdragon.petcollars.client.CollarRenderHelper;
import mcenderdragon.petcollars.common.collar.CollarCapProvider;
import mcenderdragon.petcollars.common.collar.DynamicCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PetCollarsMain.MODID)
public class PetCollarsMain 
{
	public static final String MODID = "petcollars";
	
	 // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getFormatterLogger("Pet Collars");

    @CapabilityInject(ICollar.class)
    public static Capability<ICollar> COLLAR = null;
    
    public static TileEntityType<TileEntityCollarCrafter> type_collar_crafter;
    public static Block collar_crafter;
    
	public PetCollarsMain()
	{
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		modBus.addListener(this::setup);
		DistExecutor.runWhenOn(Dist.CLIENT, ()-> ()-> modBus.addListener(CollarRenderHelper::clientSetup));
		modBus.addListener(this::sendIMC);
		modBus.addListener(this::collectIMC);
		
		modBus.addGenericListener(Block.class, this::registerBlocks);
		modBus.addGenericListener(TileEntityType.class, this::registerTileEntityType);
		
		MinecraftForge.EVENT_BUS.register(this);
		
		PendantRegistry.init();
	}
	
	private void setup(final FMLCommonSetupEvent event)
    {
		CapabilityManager.INSTANCE.register(ICollar.class, new IStorage<ICollar>()
        {
            @Override
            public INBT writeNBT(Capability<ICollar> capability, ICollar instance, Direction side)
            {
               	return null;
            }

            @Override
            public void readNBT(Capability<ICollar> capability, ICollar instance, Direction side, INBT nbt)
            {
               
            }
        },
        () -> new DynamicCollarInstance(new INBTSerializable[0], new PendantBase[0], null, null));
		
        PacketHandler.init();
        PendantRegistry.bakeCollarList();
        CollarRecipeManager.init();
    }
	
	private final void sendIMC(InterModEnqueueEvent event)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> CollarRenderHelper::registerCollarRenderers);
	}
	
	private final void collectIMC(InterModProcessEvent event)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> CollarRenderHelper.processIMCs(event.getIMCStream(s -> s.startsWith("rendering"))));
	}
	
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		collar_crafter = new BlockCollarCrafter(Block.Properties.create(Material.ROCK, MaterialColor.BLACK));
		collar_crafter.setRegistryName(MODID, "collar_crafter");
		event.getRegistry().register(collar_crafter);
	}
	
	public void registerTileEntityType(RegistryEvent.Register<TileEntityType<?>> event)
	{
		type_collar_crafter = new TileEntityType<TileEntityCollarCrafter>(TileEntityCollarCrafter::new, ImmutableSet.of(collar_crafter), null);
		type_collar_crafter.setRegistryName(MODID, "collar_crafter");
		event.getRegistry().register(type_collar_crafter);
	}
	
	@SubscribeEvent
	public void attachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof AnimalEntity)
		{
			CollarCapProvider cap = new CollarCapProvider((AnimalEntity) event.getObject());
			event.addCapability(new ResourceLocation(MODID, "collar"), cap);
			event.addListener(cap::invalidate);
		}
	}
}
