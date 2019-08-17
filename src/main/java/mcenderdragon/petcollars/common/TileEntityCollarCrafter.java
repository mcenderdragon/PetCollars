package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.item.ItemCollarBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCollarCrafter extends TileEntity 
{

	ItemStackHandler handler = new ItemStackHandler(6);
	
	public TileEntityCollarCrafter(TileEntityType<?> tileEntityTypeIn) 
	{
		super(tileEntityTypeIn);
	}

	private static class ItemStackHandlerImpl extends ItemStackHandler
	{
		public ItemStackHandlerImpl() 
		{
			super(6);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) 
		{
			if(slot == 0)
			{
				if(stack.getItem() instanceof ItemCollarBase)
				{
					
				}
			}
			return super.isItemValid(slot, stack);
		}
	}
}
