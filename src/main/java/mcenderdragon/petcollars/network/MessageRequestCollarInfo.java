package mcenderdragon.petcollars.network;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class MessageRequestCollarInfo
{
	private int id;
	
	public MessageRequestCollarInfo(AnimalEntity animal)
	{
		this(animal.getEntityId());
		if(!animal.getEntityWorld().isRemote)
			throw new IllegalArgumentException("This packet is designed for client use!");
	}
	
	private MessageRequestCollarInfo(int entityId)
	{
		this.id = entityId;
	}
	
	public static void encode(MessageRequestCollarInfo msg, PacketBuffer buf)
	{
		buf.writeVarInt(msg.id);
	}
	
	public static MessageRequestCollarInfo decode(PacketBuffer buf)
	{
		return new MessageRequestCollarInfo(buf.readVarInt());
	}
	
	public static void conume(MessageRequestCollarInfo msg, Supplier<NetworkEvent.Context> context)
	{
		NetworkEvent.Context ctx = context.get();
		ServerPlayerEntity pl = ctx.getSender();
		
		Entity e = pl.getServerWorld().getEntityByID(msg.id);
		if(e!=null)
		{
			PacketHandler.CHANNEL_PET_COLLARS.send(PacketDistributor.PLAYER.with(ctx::getSender), new MessageResponseCollarInfo(e));
		}
		ctx.setPacketHandled(true);
	}
}
