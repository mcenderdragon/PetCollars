package mcenderdragon.petcollars.client;

import java.util.WeakHashMap;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.network.MessageRequestCollarInfo;
import mcenderdragon.petcollars.network.MessageResponseCollarInfo;
import mcenderdragon.petcollars.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CollarRenderHelper 
{
	private static WeakHashMap<Entity, ClientCollarState> map = new WeakHashMap<Entity, CollarRenderHelper.ClientCollarState>();
	
	public static final ClientCollarState UNKNOWN = new ClientCollarState(null, null);
	public static final ClientCollarState REQUESTED = new ClientCollarState(null, null);
	public static final ClientCollarState NO_COLLAR = new ClientCollarState(null, null);
	
	
	public static void onMessage(MessageResponseCollarInfo msg)
	{
		ClientWorld world = Minecraft.getInstance().world;
		
		Entity e = world.getEntityByID(msg.entityId);
		if(e!=null)
		{
			if(!msg.hasCollar)
			{
				map.put(e, NO_COLLAR);
			}
			else
			{
				map.put(e, new ClientCollarState(msg.collarItem, msg.pendants));
			}
		}
	}
	
	public static ClientCollarState getCollarState(AnimalEntity e)
	{
		ClientCollarState state = map.getOrDefault(e, UNKNOWN);
		if(state == UNKNOWN)
		{
			PacketHandler.CHANNEL_PET_COLLARS.sendToServer(new MessageRequestCollarInfo(e));
			map.put(e, REQUESTED);
		}
		return state;
		
	}
	
	public static class ClientCollarState
	{
		final Item collarItem;
		final PendantBase<INBTSerializable<CompoundNBT>>[] pendants;
		
		private ItemStack stack;
		
		public ClientCollarState(Item collarItem, PendantBase<INBTSerializable<CompoundNBT>>[] pendants) 
		{
			super();
			this.collarItem = collarItem;
			this.pendants = pendants;
		}
		
		public boolean hasCollar()
		{
			return collarItem != null && pendants != null;
		}

		public Item getCollarItem() 
		{
			return collarItem;
		}

		public PendantBase<INBTSerializable<CompoundNBT>>[] getPendants() 
		{
			return pendants;
		}
		
		public boolean isRequested()
		{
			return this == REQUESTED;
		}
		
		public boolean isUnknown()
		{
			return this == UNKNOWN;
		}
		
		public boolean isKnown()
		{
			return !isUnknown() && !isRequested();
		}
		
		public ItemStack getAsItem()
		{
			if(stack ==  null)
			{
				if(hasCollar())
				{
					stack = HelperCollars.createCollarStack(collarItem, pendants, new INBTSerializable[pendants.length]);
				}
				else
				{
					stack = ItemStack.EMPTY;
				}
			}
			return stack;
		}
	}
}
