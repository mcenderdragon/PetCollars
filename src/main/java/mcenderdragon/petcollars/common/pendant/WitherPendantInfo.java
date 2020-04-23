package mcenderdragon.petcollars.common.pendant;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class WitherPendantInfo implements INBTSerializable<CompoundNBT>
{
	private static final float base_health = 20F;
	
	protected int level = 0;
	protected float healed = 0F;

	@Override
	public CompoundNBT serializeNBT() 
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("lvl", level);
		nbt.putFloat("healed", healed);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) 
	{
		level = nbt.getInt("lvl");
		healed = nbt.getFloat("healed");
	}
	
	protected void onHealed(float f)
	{
		healed += f;
		while(healed > base_health*level)
		{
			healed -= base_health*level;
			level++;
		}
	}

	public float getHealModifier() 
	{
		double scaled_lvl = (1+level) * 0.1;
		double mod = (-1/(scaled_lvl+1) + 1);
		return (float) mod;
	}

}
