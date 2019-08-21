package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.collar.DynamicCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.item.ItemList;
import mcenderdragon.petcollars.common.item.ItemPendantBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityCollarCrafter extends TileEntity 
{

	ItemStackHandlerImpl handler = new ItemStackHandlerImpl();
	
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
		
		public boolean isEmpty()
		{
			for(int i=0;i<getSlots();i++)
			{
				if(!getStackInSlot(i).isEmpty())
					return false;
			}
			return true;
		}
		
	}

	public ItemStack addItem(ItemStack heldItem) 
	{
		if(heldItem.getChildTag("pendants")!=null && heldItem.getItem() instanceof ItemCollarBase)
		{
			if(!handler.isEmpty())
				return heldItem;
			
			ItemCollarBase collarItem = (ItemCollarBase) heldItem.getItem();
			
			if(collarItem.pendantAmount <= 5)
			{
				handler.setStackInSlot(0, new ItemStack(collarItem));
				
				CompoundNBT nbt = heldItem.getChildTag("pendants");
				PendantBase<INBTSerializable<CompoundNBT>>[] pendants = HelperCollars.loadPendants(nbt);
				INBTSerializable<CompoundNBT>[] data = HelperCollars.loadAdditionalInfo(nbt, pendants);
				
				for(int i=0;i<collarItem.pendantAmount;i++)
				{
					if(pendants[i]!=null)
					{
						ResourceLocation id = pendants[i].getRegistryName();
						ItemStack pendantStack;
						if(ForgeRegistries.ITEMS.containsKey(id))
						{
							pendantStack = new ItemStack(ForgeRegistries.ITEMS.getValue(id));
							pendantStack.setTag(new CompoundNBT());
						}
						else
						{
							pendantStack = new ItemStack(ItemList.dummy_pendant);
							pendantStack.setTag(new CompoundNBT());
							pendantStack.getTag().putString("dummy", id.toString());
						}
						pendantStack.getTag().put("pendant", data[i].serializeNBT());
						
						handler.setStackInSlot(i+1, pendantStack);
					}
				}
				markDirty();
				heldItem.split(1);
				return heldItem;
			}
			else
			{
				return heldItem;
			}
		}
		
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
	
	public void tryRemoveFromSlot(int slot)
	{
		ItemStack stack = ItemStack.EMPTY;
		if(slot == 0)
		{
			for(int i=1;i<handler.getSlots();i++)
			{
				if(!handler.getStackInSlot(i).isEmpty())
					return;
			}
			stack = handler.extractItem(0, 64, false);
		}
		else
		{
			stack = handler.extractItem(slot, 64, false);
		}
		
		
		if(!stack.isEmpty())
		{
			ItemEntity ent = new ItemEntity(world, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, stack);
			ent.world.addEntity(ent);
			markDirty();
		}
	}
}
