package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.item.ItemPendantBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
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
	
	@Override
	public CompoundNBT getUpdateTag() 
	{
		return write(new CompoundNBT());
	}
	
	@Override
	public void handleUpdateTag(CompoundNBT tag) 
	{
		super.handleUpdateTag(tag);
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() 
	{
		return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) 
	{
		handleUpdateTag(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}
	
	@SuppressWarnings("unchecked")
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
			
			markDirty();
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
				if(stack.getItem() instanceof ItemCollarBase)
				{
					return !stack.hasTag();
				}
				else
				{
					return false;
				}
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
			ItemStack is = handler.insertItem(0, heldItem, false);
			markDirty();
			return is;
		}
		else
		{
			ItemCollarBase collar = (ItemCollarBase) handler.getStackInSlot(0).getItem();
			for(int i=0;i<collar.pendantAmount;i++)
			{
				ItemStack inserted = handler.insertItem(i+1, heldItem, false);
				if(inserted!=heldItem)
				{
					markDirty();
					return inserted;
				}
			}
			return heldItem;
		}
		
	}

	public IItemHandler getHandler() 
	{
		return handler;
	}
	
	@Override
	public void markDirty() 
	{
		super.markDirty();
		SUpdateTileEntityPacket pkt = this.getUpdatePacket();
		world.getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(25)).stream().map(p -> p.connection).forEach(c -> c.sendPacket(pkt));
	}
}
