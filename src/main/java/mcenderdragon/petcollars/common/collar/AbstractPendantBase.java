package mcenderdragon.petcollars.common.collar;

import mcenderdragon.petcollars.common.ICollar;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractPendantBase<T extends INBTSerializable<CompoundNBT>> 
{
	/**
	 * 
	 * @return time between update. 0 means every tick (so 20 per second). 1 means every second tick. numbers below 0 means disabled 
	 */
	public int getUpdateFrequenzy()
	{
		return -1;
	}
	
	/**
	 * @return true if the {@link AbstractPendantBase#update(AnimalEntity)} method should be called.
	 */
	public boolean needsUpdate()
	{
		return getUpdateFrequenzy() >= 0;
	}
	
	/**
	 * 
	 * @param animal The animal wearing this pendant
	 * @param collar the collar this pedant is attached to.
	 * @param customInfo additional information if needed
	 */
	public void update(AnimalEntity animal, AbstractCollarInstance collar, T customInfo)
	{
		
	}
	
	/**
	 * 
	 * @param nbt the saved nbt from the customInfo
	 * @return a new instance of the customInfo
	 */
	public abstract T deserialize(CompoundNBT nbt);
	
	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingAttackEvent} so before armor or any other buffs got applied to reduce the damage
	 * 
	 * @param animal The animal wearing this pendant
	 * @param src the Source where the Damage is coming from
	 * @param rawDamage the raw damage amount
	 * @param collar the collar this pedant is attached to.
	 * @param customInfo additional information if needed
	 */
	public void onAnimalAttackedBy(AnimalEntity animal, DamageSource src, float rawDamage, ICollar collar, T customInfo)
	{
		
	}
	
	/**
	 * This is fired from the {@link net.minecraftforge.event.entity.living.LivingDamageEvent} so after armor or any other buffs got applied to reduce the damage
	 * 
	 * @param animal The animal wearing this pendant
	 * @param src the Source where the Damage is coming from
	 * @param damage the raw damage amount
	 * @param collar the collar this pedant is attached to.
	 * @param customInfo additional information if needed
	 */
	public void onAnimalDamagedBy(AnimalEntity animal, DamageSource src, float damage, ICollar collar, T customInfo)
	{
		
	}
}
