package mcenderdragon.petcollars.client.color;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.item.ItemList;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.command.arguments.NBTTagArgument;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemColoring 
{
	
	public static void setupColoring()
	{
		ItemColors itemC = Minecraft.getInstance().getItemColors();
		BlockColors blockC = Minecraft.getInstance().getBlockColors();
		
		itemC.register(ItemColoring::getColorFromCollar, ItemList.leather_collar);
	}
		
	public static int getColorFromCollar(ItemStack stack, int layer)
	{
		if(layer == 1)
		{
			CompoundNBT nbt = stack.getChildTag("pendants");
			if(nbt !=null)
			{
				if(!nbt.contains("color1"))
				{
					PendantBase<INBTSerializable<CompoundNBT>> pendant = HelperCollars.loadPendants(nbt)[0];
					int color = 0;
					if(pendant!=null)
					{
						color = pendant.getColor();
						nbt.putInt("color1", color);
						return color;
					}
				}
				return nbt.getInt("color1");
			}
		}
		return 0xFFFFFF;
	}
}
