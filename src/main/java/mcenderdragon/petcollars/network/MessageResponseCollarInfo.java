package mcenderdragon.petcollars.network;

import java.util.function.Supplier;

import mcenderdragon.petcollars.client.CollarRenderHelper;
import mcenderdragon.petcollars.common.PendantRegistry;
import mcenderdragon.petcollars.common.PetCollarsMain;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class MessageResponseCollarInfo 
{
	public final int entityId;
	public final boolean hasCollar;
	public final PendantBase<INBTSerializable<CompoundNBT>>[] pendants;
	public final Item collarItem;
	
	public MessageResponseCollarInfo(Entity entity)
	{
		entityId = entity.getEntityId();
		LazyOptional<ICollar> supplier = entity.getCapability(PetCollarsMain.COLLAR);
		if(supplier.isPresent())
		{
			ICollar col = supplier.orElse(null);
			pendants = col.getAllPendants();
			collarItem = col.asItem();
			
			if(ForgeRegistries.ITEMS.containsValue(collarItem))
			{
				hasCollar = true;
			}
			else
			{
				hasCollar = false;
				PetCollarsMain.LOGGER.warn("Collar item is not registered!!! %s", collarItem);
			}
		}
		else
		{
			hasCollar = false;
			pendants = null;
			collarItem = null;
		}
	}
	
	private MessageResponseCollarInfo(int entityId, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, Item collarItem)
	{
		this.entityId = entityId;
		this.pendants = pendants;
		this.collarItem = collarItem;
		this.hasCollar = pendants!=null && collarItem != null;
	}
	
	
	public static void encode(MessageResponseCollarInfo msg, PacketBuffer buf)
	{
		buf.writeVarInt(msg.entityId);
		if(msg.hasCollar)
		{
			buf.writeByte(msg.pendants.length);
				
			buf.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, msg.collarItem);
				
			for(PendantBase<INBTSerializable<CompoundNBT>> pendant : msg.pendants)
			{
				buf.writeRegistryIdUnsafe(PendantRegistry.PENDANT_REGISTRY, pendant);
			}
		}
		else
		{
			buf.writeByte(-1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static MessageResponseCollarInfo decode(PacketBuffer buf)
	{
		int entityID = buf.readVarInt();
		byte length = buf.readByte();

		if(length==-1)
		{
			//No collar
			return new MessageResponseCollarInfo(entityID, null, null);
		}
		else
		{
			PendantBase<INBTSerializable<CompoundNBT>>[] pendants = new PendantBase[length];
			Item collarItem = buf.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
			for(int i=0;i<pendants.length;i++)
			{
				pendants[i] = (PendantBase<INBTSerializable<CompoundNBT>>) buf.readRegistryIdUnsafe(PendantRegistry.PENDANT_REGISTRY);
			}
			
			return new MessageResponseCollarInfo(entityID, pendants, collarItem);
		}
	}
	
	public static void conume(MessageResponseCollarInfo msg, Supplier<NetworkEvent.Context> context)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> msg::executeOnClient);
		context.get().setPacketHandled(true);
	}
	
	private void executeOnClient()
	{
		CollarRenderHelper.onMessage(this);
	}
}
