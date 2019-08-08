package mcenderdragon.petcollars.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PetCollarsMain.MODID)
public class PetCollarsMain 
{
	public static final String MODID = "petcollars";
	
	 // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getFormatterLogger("Pet Collars");

    @CapabilityInject(ICollar.class)
    public static Capability<ICollar> COLLAR = null;
    
	public PetCollarsMain()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
    {
		CapabilityManager.INSTANCE.register(ICollar.class, new IStorage<ICollar>()
        {
            @Override
            public INBT writeNBT(Capability<ICollar> capability, ICollar instance, Direction side)
            {
               	
            }

            @Override
            public void readNBT(Capability<ICollar> capability, ICollar instance, Direction side, INBT nbt)
            {
               
            }
        },
        () -> new EnergyStorage(1000));
		
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> %s", Blocks.DIRT.getRegistryName());
    }
	
	@SubscribeEvent
	public void attachCapabilitiesEvent(AttachCapabilitiesEvent<AnimalEntity> event)
	{
		event.addCapability(new ResourceLocation(MODID, "collar"), new ICapabilityProvider()
		{
			
			@Override
			public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
