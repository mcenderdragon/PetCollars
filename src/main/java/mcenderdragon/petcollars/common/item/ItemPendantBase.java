package mcenderdragon.petcollars.common.item;

import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemPendantBase extends Item 
{
	private final PendantBase<?> pendant;

	public ItemPendantBase(Properties properties, PendantBase<?> pendant) 
	{
		super(properties);
		this.pendant = pendant;
	}

	public PendantBase<?> getPendant(ItemStack it)
	{
		return pendant;
	}
	
	public INBTSerializable<CompoundNBT> createAdditionalInfo(ItemStack it)
	{
		return pendant.deserialize(it.getOrCreateChildTag("pendant"));
	}
}
