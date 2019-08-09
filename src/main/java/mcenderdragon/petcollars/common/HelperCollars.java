package mcenderdragon.petcollars.common;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;


import mcenderdragon.petcollars.common.collar.CollarCapProvider;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@Mod.EventBusSubscriber(modid = PetCollarsMain.MODID)
public class HelperCollars 
{
	@Nullable
	public static ICollar getCollarFromEntity(AnimalEntity animal)
	{
		return animal.getCapability(PetCollarsMain.COLLAR).orElse(null);
	}
	
	public static boolean hasCollar(Entity e)
	{
		return e.getCapability(PetCollarsMain.COLLAR).isPresent();
	}
	
	private static List<WeakReference<AnimalEntity>> collaredEntities = new ArrayList<>(100);
	
	@SubscribeEvent
	public static void onEntityJoinedWorld(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote)
		{
			if(hasCollar(event.getEntity()))
			{
				collaredEntities.add(new WeakReference<AnimalEntity>((AnimalEntity) event.getEntity()));
			}
		}
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
	
	@SubscribeEvent
	public static void onWorldTick(TickEvent.ServerTickEvent event)
	{
		if(event.side == LogicalSide.SERVER)
		{
			if(event.phase == Phase.END)
			{
				Iterator<WeakReference<AnimalEntity>> iter = collaredEntities.iterator();
				while(iter.hasNext())
				{
					WeakReference<AnimalEntity> ref = iter.next();
					AnimalEntity animal = ref.get();
					if(animal != null && animal.isAlive())
					{
						ICollar collar = getCollarFromEntity(animal);
						if(collar != null)
						{
							collar.update();
						}
						else
						{
							iter.remove();
						}
					}
					else
					{
						iter.remove();
					}
				}
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static PendantBase<INBTSerializable<CompoundNBT>>[] loadPendants(CompoundNBT nbt)
	{
		int count = nbt.getInt("count");
		
		PendantBase<INBTSerializable<CompoundNBT>>[] pb = new PendantBase[count];
		for(int i=0;i<count;i++)
		{
			if(nbt.contains("p"+i))
			{
				PendantBase<?> pendant = PendantRegistry.PENDANT_REGISTRY.getValue(new ResourceLocation(nbt.getString("p"+i)));
				pb[i] = (PendantBase<INBTSerializable<CompoundNBT>>) pendant;
			}
		}
		
		return pb;
	}
	
	public static INBTSerializable<CompoundNBT>[] loadAdditionalInfo(CompoundNBT nbt, PendantBase<INBTSerializable<CompoundNBT>>[] pendants)
	{
		int count = pendants.length;
		@SuppressWarnings("unchecked")
		INBTSerializable<CompoundNBT>[] infos = new INBTSerializable[count];
		for(int i=0;i<count;i++)
		{
			if(nbt.contains("i"+i))
			{
				PendantBase<?> pendant = pendants[i];
				infos[i] = pendant.deserialize(nbt.getCompound("i" + i));
			}
		}
		return infos;
	}
	
	public static void saveToNBT(CompoundNBT nbt, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, INBTSerializable<CompoundNBT>[] moreInfo)
	{
		if(pendants.length != moreInfo.length)
			throw new IllegalStateException("Pendant amount and data amount differ! P:" + pendants.length  + " != D:" + moreInfo.length);
		
		nbt.putInt("count", pendants.length);
		
		for(int i=0;i<pendants.length;i++)
		{
			PendantBase<INBTSerializable<CompoundNBT>> pb = pendants[i];
			if(pb!=null)
			{
				nbt.putString("p"+i, pb.getRegistryName().toString());
				if(moreInfo[i]!=null)
				{
					CompoundNBT savedNBT = moreInfo[i].serializeNBT();
					if(savedNBT!=null)
					{
						nbt.put("i"+i,savedNBT);
					}
				}
			}
			
		}
	}

	public static void attachCollar(ItemStack stack, ItemCollarBase itemCollarBase, AnimalEntity target) 
	{
		ICollar col = itemCollarBase.createCollar(stack, target);
		CollarCapProvider.addCollar(col, target);
		
		collaredEntities.add(new WeakReference<AnimalEntity>(target));
	}
}
