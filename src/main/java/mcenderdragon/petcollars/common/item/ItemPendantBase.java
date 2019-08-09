package mcenderdragon.petcollars.common.item;

import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.item.Item;

public class ItemPendantBase extends Item 
{
	private final PendantBase<?> pendant;

	public ItemPendantBase(Properties properties, PendantBase<?> pendant) 
	{
		super(properties);
		this.pendant = pendant;
	}

	public PendantBase<?> getPendant()
	{
		return pendant;
	}
}
