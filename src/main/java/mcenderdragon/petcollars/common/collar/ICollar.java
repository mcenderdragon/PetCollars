package mcenderdragon.petcollars.common.collar;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICollar 
{
	public void update();
	
	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingAttackEvent} so before armor or any other buffs got applied to reduce the damage
	 * 
	 * @param src the Source where the Damage is coming from
	 * @param rawDamage the raw damage amount
	 */
	public default void onAnimalAttackedBy(DamageSource src, float rawDamage)
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] pendants  = gettAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
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
		PendantBase<INBTSerializable<CompoundNBT>>[] pendants  = gettAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			pendants[i].onAnimalDamagedBy(animalFromCollar, src, damage, this, additionalInfo[i]);
		}
	}
	
	public ItemStack returnToItemStack();
	
	public PendantBase<INBTSerializable<CompoundNBT>>[] gettAllPendants();
	
	public PendantBase<INBTSerializable<CompoundNBT>>[] getTickablePendants();
	
	public INBTSerializable<CompoundNBT>[] getAllAdditionalInfo();
	
	public AnimalEntity getAnimal();
}
