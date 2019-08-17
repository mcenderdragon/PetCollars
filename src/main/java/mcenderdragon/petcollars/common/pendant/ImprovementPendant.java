package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class ImprovementPendant extends PendantBase<ImprovementInfo> 
{

	@Override
	public ImprovementInfo deserialize(CompoundNBT nbt) 
	{
		return new ImprovementInfo(nbt);
	}

	@Override
	public void onAnimalAttackedBy(AnimalEntity animal, DamageSource src, float rawDamage, ICollar collar, ImprovementInfo customInfo) 
	{
		customInfo.addDamage(rawDamage);
		super.onAnimalDamagedBy(animal, src, rawDamage, collar, customInfo);
	}
	
	@Override
	public void onAnimalAttacking(AnimalEntity animal, LivingEntity target, float amount, DamageSource source, ICollar collar, ImprovementInfo customInfo) 
	{
		customInfo.addAttackDamage(amount);
		super.onAnimalAttacking(animal, target, amount, source, collar, customInfo);
	}
	
	@Override
	public int getUpdateFrequenzy() 
	{
		return 19;
	}
	
	@Override
	public void update(AnimalEntity animal, AbstractCollarInstance collar, ImprovementInfo customInfo) 
	{
		animal.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 20, customInfo.getArmorLvl()));
		animal.addPotionEffect(new EffectInstance(Effects.STRENGTH, 20, customInfo.getArmorLvl()));
		
		super.update(animal, collar, customInfo);
	}
	
	@Override
	public int getColor() 
	{
		double d = (System.currentTimeMillis() % 5000) / 5000D;
		d = Math.cos(Math.PI * 2 * d)*0.5 + 0.5;
		int green = (int)(150 * d);
		return 0x256845 + (green <<8);
	}
}
