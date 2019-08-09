package mcenderdragon.petcollars.common.pendant;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CooldownInfo implements INBTSerializable<CompoundNBT>
{

	public int cooldown;
	
	public CooldownInfo(CompoundNBT nbt) 
	{
		deserializeNBT(nbt);
	}

	@Override
	public CompoundNBT serializeNBT() 
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("cooldown", cooldown);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) 
	{
		cooldown = nbt.getInt("cooldown");
	}

}
