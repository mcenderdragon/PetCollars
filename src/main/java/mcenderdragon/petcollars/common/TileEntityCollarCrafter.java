package mcenderdragon.petcollars.common;

import java.util.Arrays;

import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.item.ItemPendantBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCollarCrafter extends TileEntity 
{

	ItemStackHandler handler = new ItemStackHandlerImpl();
	
	public TileEntityCollarCrafter() 
	{
		super(PetCollarsMain.type_collar_crafter);
	}
	
	@Override
	public void read(CompoundNBT compound) 
	{
		super.read(compound);
		handler.deserializeNBT(compound.getCompound("items"));
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) 
	{
		compound.put("items", handler.serializeNBT());
		return super.write(compound);
	}

	public boolean tryCraft()
	{
		if(!handler.getStackInSlot(0).isEmpty())
		{
			ItemCollarBase collar = (ItemCollarBase) handler.getStackInSlot(0).getItem();
			for(int i=0;i<collar.pendantAmount;i++)
			{
				if(handler.getStackInSlot(i+1).isEmpty())
				{
					return false;
				}
			}
			
			PendantBase<INBTSerializable<CompoundNBT>>[] pendants = new PendantBase[collar.pendantAmount];
			INBTSerializable<CompoundNBT>[] data = new INBTSerializable[collar.pendantAmount];
			for(int i=0;i<collar.pendantAmount;i++)
			{
				ItemStack st = handler.getStackInSlot(i+1);
				ItemPendantBase base = (ItemPendantBase) st.getItem();
				pendants[i] =  (PendantBase<INBTSerializable<CompoundNBT>>) base.getPendant(st);
				data[i] = base.createAdditionalInfo(st);
			}
			
			 
			ItemStack item = HelperCollars.createCollarStack(collar, pendants, data);
			
			for(int i=0;i<collar.pendantAmount;i++)
			{
				handler.setStackInSlot(i+1, ItemStack.EMPTY);
			}
			handler.setStackInSlot(0, ItemStack.EMPTY);
			
			ItemEntity entity = new ItemEntity(world, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, item);
			world.addEntity(entity);
			
			return true;
		}
		return false;
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
				return stack.getItem() instanceof ItemCollarBase;
			}
			else
			{
				return stack.getItem() instanceof ItemPendantBase;
			}
		}
		
		@Override
		public int getSlotLimit(int slot) 
		{
			return 1;
		}
		
	}

	public ItemStack addItem(ItemStack heldItem) 
	{
		if(handler.getStackInSlot(0).isEmpty())
		{
			return handler.insertItem(0, heldItem, false);
		}
		else
		{
			ItemCollarBase collar = (ItemCollarBase) handler.getStackInSlot(0).getItem();
			for(int i=0;i<collar.pendantAmount;i++)
			{
				ItemStack inserted = handler.insertItem(i+1, heldItem, false);
				if(inserted!=heldItem)
					return inserted;
			}
			return heldItem;
		}
		
	}
}
