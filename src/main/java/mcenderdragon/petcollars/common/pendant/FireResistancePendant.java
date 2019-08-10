package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ServerWorld;

public class FireResistancePendant extends PendantBase<CooldownInfo>
{

	@Override
	public CooldownInfo deserialize(CompoundNBT nbt) 
	{
		return new CooldownInfo(nbt);
	}
	
	@Override
	public int getUpdateFrequenzy() 
	{
		return 19;//should be once per second
	}
	
	@Override
	public void update(AnimalEntity animal, AbstractCollarInstance collar, CooldownInfo customInfo) 
	{
		if(customInfo.cooldown>0)
		{
			customInfo.cooldown--;
			if(customInfo.cooldown == 0)
			{
				((ServerWorld)animal.world).spawnParticle(ParticleTypes.HAPPY_VILLAGER, animal.posX, animal.posY+animal.getEyeHeight(), animal.posZ, 25, 0.5, 0.5, 0.5, 0.1F);
			}
		}
		
		super.update(animal, collar, customInfo);
	}

	@Override
	public void onAnimalAttackedBy(AnimalEntity animal, DamageSource src, float rawDamage, ICollar collar, CooldownInfo customInfo) 
	{
		if(src.isFireDamage())
		{
			if(customInfo.cooldown<=0)
			{
				customInfo.cooldown = 30; //30 seconds second cool down
				animal.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 50)); //2.5 seconds
			}
		}
		super.onAnimalAttackedBy(animal, src, rawDamage, collar, customInfo);
	}
	
	@Override
	public int getColor() 
	{
		return 0xff6600;
	}
}
