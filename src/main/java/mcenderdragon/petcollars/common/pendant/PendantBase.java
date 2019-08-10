package mcenderdragon.petcollars.common.pendant;

import java.awt.Color;

import mcenderdragon.petcollars.common.PendantRegistry;
import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class PendantBase<T extends INBTSerializable<CompoundNBT>> extends ForgeRegistryEntry<PendantBase<?>> 
{
	
	protected String translationKey;
	
	/**
	 * 
	 * @return time between update. 0 means every tick (so 20 per second). 1 means every second tick. numbers below 0 means disabled 
	 */
	public int getUpdateFrequenzy()
	{
		return -1;
	}
	
	/**
	 * @return true if the {@link PendantBase#update(AnimalEntity)} method should be called.
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
	
	/**
	 * @return the color of the pendant on a collar
	 */
	public int getColor()
	{
		return Color.HSBtoRGB( System.currentTimeMillis()%1000 /1000F, 1F, 1F);
	}

	public ITextComponent getName() 
	{
		return new TranslationTextComponent(getTranslationKey());
	}

	public String getTranslationKey() 
	{
		if(translationKey == null)
		{
			this.translationKey = Util.makeTranslationKey("pendant", PendantRegistry.PENDANT_REGISTRY.getKey(this));
		}
		return translationKey;
	}
}
