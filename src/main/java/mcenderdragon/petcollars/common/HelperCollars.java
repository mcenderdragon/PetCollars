package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PetCollarsMain.MODID)
public class HelperCollars 
{
	public static AbstractCollarInstance getCollarFromEntity(AnimalEntity animal)
	{
		
	}
	
	public static boolean hasCollar(AnimalEntity animal)
	{
		
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
			AbstractCollarInstance collar = getCollarFromEntity((AnimalEntity) event.getEntityLiving());
			collar.onAnimalAttackedBy(event.getSource(), event.getAmount());
		}
	}
	
	@SubscribeEvent
	public static void onLivingDamaged(LivingDamageEvent event)
	{
		
	}
}
