package mcenderdragon.petcollars.common.collar;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class DynamicCollarInstance extends AbstractCollarInstance 
{

	public DynamicCollarInstance(CompoundNBT savedNBT, AnimalEntity animalFromCollar) 
	{
		this(loadInfo(savedNBT), loadPendants(savedNBT), animalFromCollar);
	}
	
	private static PendantBase<INBTSerializable<CompoundNBT>>[] loadPendants(CompoundNBT nbt)
	{
		return null;//TODO
	}
	
	private static INBTSerializable<CompoundNBT>[] loadInfo(CompoundNBT nbt)
	{
		return null;//TODO
	}
	
	public DynamicCollarInstance(INBTSerializable<CompoundNBT>[] additionalInfo, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, AnimalEntity animalFromCollar) 
	{
		super(additionalInfo, pendants, animalFromCollar);
		
	}

	@Override
	public ItemStack returnToItemStack() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
