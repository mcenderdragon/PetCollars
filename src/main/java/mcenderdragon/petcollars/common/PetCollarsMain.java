package mcenderdragon.petcollars.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mcenderdragon.petcollars.client.CollarRenderHelper;
import mcenderdragon.petcollars.client.color.ItemColoring;
import mcenderdragon.petcollars.client.rendering.CollarRendererLayer;
import mcenderdragon.petcollars.common.collar.CollarCapProvider;
import mcenderdragon.petcollars.common.collar.DynamicCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.network.PacketHandler;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.INBT;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
    
	public PetCollarsMain()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::sendIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::collectIMC);
		
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
    }
	
	private final void clientSetup(FMLClientSetupEvent event)
	{
		EntityRendererManager manager = event.getMinecraftSupplier().get().getRenderManager();
		manager.renderers.forEach( (clazz,renderer) -> {
			if(AnimalEntity.class.isAssignableFrom(clazz))
			{
				if(renderer instanceof LivingRenderer)
				{
					((LivingRenderer) renderer).addLayer(new CollarRendererLayer((LivingRenderer) renderer));
				}
			}
		});
		ItemColoring.setupColoring();
	}
	
	private final void sendIMC(InterModEnqueueEvent event)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> CollarRenderHelper::registerCollarRenderers);
	}
	
	private final void collectIMC(InterModProcessEvent event)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> CollarRenderHelper.processIMCs(event.getIMCStream(s -> s.startsWith("rendering"))));
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
