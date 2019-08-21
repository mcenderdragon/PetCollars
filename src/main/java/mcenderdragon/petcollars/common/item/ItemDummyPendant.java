package mcenderdragon.petcollars.common.item;

import mcenderdragon.petcollars.common.PendantRegistry;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemDummyPendant extends ItemPendantBase 
{

	public ItemDummyPendant(Properties properties, PendantBase<?> pendant) 
	{
		super(properties, pendant);
	}
	
	public PendantBase<?> getPendant(ItemStack it)
	{
		if(it.hasTag())
		{
			if(it.getTag().contains("dummy"))
			{
				return PendantRegistry.PENDANT_REGISTRY.getValue(new ResourceLocation(it.getTag().getString("dummy")));
			}
		}
		return super.getPendant(it);
	}
}
