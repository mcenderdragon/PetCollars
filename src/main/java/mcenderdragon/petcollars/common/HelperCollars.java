package mcenderdragon.petcollars.common;

import javax.annotation.Nullable;

import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PetCollarsMain.MODID)
public class HelperCollars 
{
	@Nullable
	public static ICollar getCollarFromEntity(AnimalEntity animal)
	{
		return animal.getCapability(PetCollarsMain.COLLAR).orElse(null);
	}
	
	public static boolean hasCollar(AnimalEntity animal)
	{
		return animal.getCapability(PetCollarsMain.COLLAR).isPresent();
	}
	
	
	@SubscribeEvent
	public static void onEntityJoinedWorld(EntityJoinWorldEvent event)
	{
		
	}
	
	@SubscribeEvent
	public static void onLivingAttacked(LivingAttackEvent event)
	{
		if(event.getEntityLiving() instanceof AnimalEntity)
		{
			ICollar collar = getCollarFromEntity((AnimalEntity) event.getEntityLiving());
			if(collar!=null)
				collar.onAnimalAttackedBy(event.getSource(), event.getAmount());
		}
	}
	
	@SubscribeEvent
	public static void onLivingDamaged(LivingDamageEvent event)
	{
		if(event.getEntityLiving() instanceof AnimalEntity)
		{
			ICollar collar = getCollarFromEntity((AnimalEntity) event.getEntityLiving());
			if(collar!=null)
				collar.onAnimalDamagedBy(event.getSource(), event.getAmount());
		}
	}
}
