package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.AbstractCollarInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class GlowingPendant extends PendantBase<DummyAdditionalInfo>
{

	@Override
	public DummyAdditionalInfo deserialize(CompoundNBT nbt) 
	{
		return new DummyAdditionalInfo();
	}

	@Override
	public int getUpdateFrequenzy() 
	{
		return 19;
	}
	
	@Override
	public void update(AnimalEntity animal, AbstractCollarInstance collar, DummyAdditionalInfo customInfo) 
	{
		animal.addPotionEffect(new EffectInstance(Effects.GLOWING, 21));
	}
	
	@Override
	public int getColor() 
	{
		return 0x66e9ff;
	}
}
