package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class WitherPendant extends PendantBase<WitherPendantInfo> 
{

	@Override
	public WitherPendantInfo deserialize(CompoundNBT nbt) 
	{
		WitherPendantInfo info = new WitherPendantInfo();
		info.deserializeNBT(nbt);
		return info;
	}

	
	@Override
	public void onAnimalAttacking(AnimalEntity animal, LivingEntity target, float amount, DamageSource source, ICollar collar, WitherPendantInfo customInfo) 
	{
		float f = amount * customInfo.getHealModifier();
		customInfo.onHealed(f);
		animal.heal(f);
		
		if(animal instanceof TameableEntity)
		{
			TameableEntity tamed = (TameableEntity) animal;
			LivingEntity owner = tamed.getOwner();
			if(owner!=null)
			{
				int regenerationLvl = customInfo.level / 10;
				if(regenerationLvl > 5)
					regenerationLvl = 5;
				int ticksperHP;
				switch (regenerationLvl)
				{
				case 0:
					ticksperHP = 50;
					break;
				case 1:
					ticksperHP = 25;
					break;
				case 2:
					ticksperHP = 12;
					break;
				case 3:
					ticksperHP = 6;
					break;
				case 4:
					ticksperHP = 3;
					break;
				case 5:
				default:
					ticksperHP = 1;
					break;
				}
				
				int ticksNeeded = (int) (f * ticksperHP);
				
				EffectInstance eff = new EffectInstance(Effects.REGENERATION, ticksNeeded, regenerationLvl);
				owner.addPotionEffect(eff);
			}
		}

		super.onAnimalAttacking(animal, target, amount, source, collar, customInfo);
	}
	
	@Override
	public int getColor() 
	{
		return 0x39343d;
	}
}
