package mcenderdragon.petcollars.common.collar;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class DummyAdditionalInfo implements INBTSerializable<CompoundNBT>
{
	
	public static final DummyAdditionalInfo DUMMY = new DummyAdditionalInfo();

	@Override
	public CompoundNBT serializeNBT() 
	{
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) 
	{	
		
	}

}
