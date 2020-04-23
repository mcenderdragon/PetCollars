package mcenderdragon.petcollars.common.collar;

import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICollar extends IItemProvider
{
	/**
	 * called each tick
	 */
	public void update();
	
	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingAttackEvent} so before armor or any other buffs got applied to reduce the damage
	 * 
	 * @param src the Source where the Damage is coming from
	 * @param rawDamage the raw damage amount
	 */
	public default void onAnimalAttackedBy(DamageSource src, float rawDamage)
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] pendants  = getAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			if(pendants[i]!=null)
				pendants[i].onAnimalAttackedBy(animalFromCollar, src, rawDamage, this, additionalInfo[i]);
		}
	}
	
	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingDamageEvent} so after armor or any other buffs got applied to reduce the damage
	 * 
	 * @param src the Source where the Damage is coming from
	 * @param damage the raw damage amount
	 */
	public default void onAnimalDamagedBy(DamageSource src, float damage)
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] pendants  = getAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			if(pendants[i]!=null)
				pendants[i].onAnimalDamagedBy(animalFromCollar, src, damage, this, additionalInfo[i]);
		}
	}
	
	public PendantBase<INBTSerializable<CompoundNBT>>[] getAllPendants();
	
	public PendantBase<INBTSerializable<CompoundNBT>>[] getTickablePendants();
	
	public INBTSerializable<CompoundNBT>[] getAllAdditionalInfo();
	
	/**
	 * 
	 * @return the Animal wearing this collar
	 */
	public AnimalEntity getAnimal();

	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingDamageEvent} so after armor or any other buffs got applied to reduce the damage
	 * 
	 * @param target the living that this animal (wearing a collar) is attacking
	 * @param amount the reduced damage dealt
	 * @param source the damage source 
	 */
	public default void onAnimalAttacking(LivingEntity target, float amount, DamageSource source)
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] pendants  = getAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			if(pendants[i]!=null)
				pendants[i].onAnimalAttacking(animalFromCollar, target, amount, source, this, additionalInfo[i]);
		}
	}
}
