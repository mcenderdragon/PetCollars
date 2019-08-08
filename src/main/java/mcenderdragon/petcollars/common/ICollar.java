package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.collar.AbstractPendantBase;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICollar 
{
	public void update();
	
	public default void onAnimalAttackedBy(DamageSource src, float rawDamage)
	{
		AbstractPendantBase<INBTSerializable<CompoundNBT>>[] pendants  = gettAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			pendants[i].onAnimalAttackedBy(animalFromCollar, src, rawDamage, this, additionalInfo[i]);
		}
	}
	
	public default void onAnimalDamagedBy(DamageSource src, float damage)
	{
		AbstractPendantBase<INBTSerializable<CompoundNBT>>[] pendants  = gettAllPendants();
		AnimalEntity animalFromCollar = getAnimal();
		INBTSerializable<CompoundNBT>[] additionalInfo = getAllAdditionalInfo();
		for(int i=0;i<pendants.length;i++)
		{
			pendants[i].onAnimalDamagedBy(animalFromCollar, src, damage, this, additionalInfo[i]);
		}
	}
	
	public ItemStack returnToItemStack();
	
	public AbstractPendantBase<INBTSerializable<CompoundNBT>>[] gettAllPendants();
	
	public AbstractPendantBase<INBTSerializable<CompoundNBT>>[] getTickablePendants();
	
	public INBTSerializable<CompoundNBT>[] getAllAdditionalInfo();
	
	public AnimalEntity getAnimal();
}
