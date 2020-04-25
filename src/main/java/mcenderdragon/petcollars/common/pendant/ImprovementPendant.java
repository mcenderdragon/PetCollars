package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
		int armor = Math.min(Math.max(0, customInfo.getArmorLvl() / 10) - 1,  4);
		if(armor >= 0)
			animal.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 21, armor));
		
		animal.addPotionEffect(new EffectInstance(Effects.STRENGTH, 21, customInfo.getAttackLvl()));
		
		if(customInfo.needsUpdate)
		{
			IAttributeInstance armorAtt = animal.getAttribute(SharedMonsterAttributes.ARMOR);
			AttributeModifier base = customInfo.getArmorModifier();
			armorAtt.removeModifier(base);
			armorAtt.applyModifier(new AttributeModifier(base.getID(), customInfo.getArmorName(), base.getAmount() * (1 + customInfo.getArmorLvl()), base.getOperation()));
			
			float hp = animal.getHealth();
			IAttributeInstance healthAtt = animal.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
			base = customInfo.getHealthModifier();
			healthAtt.removeModifier(base);
			healthAtt.applyModifier(new AttributeModifier(base.getID(), customInfo.getHealthName(), base.getAmount() * (1 + customInfo.getArmorLvl()), base.getOperation()));
			if(animal.getHealth() < hp)
			{
				animal.setHealth(hp);
			}
			
			customInfo.needsUpdate = false;
		}
		
		
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
