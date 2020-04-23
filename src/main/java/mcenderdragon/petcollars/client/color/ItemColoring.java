package mcenderdragon.petcollars.client.color;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.item.ItemList;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemColoring 
{
	
	public static void setupColoring()
	{
		ItemColors itemC = Minecraft.getInstance().getItemColors();
		BlockColors blockC = Minecraft.getInstance().getBlockColors();
		
		itemC.register(ItemColoring::getColorFromCollar, ItemList.leather_collar, ItemList.gold_collar, ItemList.nether_collar);
	}
		
	public static int getColorFromCollar(ItemStack stack, int layer)
	{
		if(layer == 1 || layer == 2 || layer == 3)
		{
			CompoundNBT nbt = stack.getChildTag("pendants");
			if(nbt !=null)
			{
				PendantBase<INBTSerializable<CompoundNBT>> pendant = HelperCollars.loadPendants(nbt)[layer -1];
				int color = 0;
				if(pendant!=null)
				{
					color = pendant.getColor();
					return color;
				}
			}
		}
		return 0xFFFFFF;
	}
}
