package mcenderdragon.petcollars.common.collar;

import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class DynamicCollarInstance extends AbstractCollarInstance 
{
	private final Item collarItem;
	
	public DynamicCollarInstance(INBTSerializable<CompoundNBT>[] additionalInfo, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, AnimalEntity animalFromCollar, Item collarItem) 
	{
		super(additionalInfo, pendants, animalFromCollar);
		this.collarItem = collarItem;
	}

	@Override
	public Item asItem() 
	{
		return collarItem;
	}

	

}
