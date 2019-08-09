package mcenderdragon.petcollars.network;

import java.util.function.Predicate;

import mcenderdragon.petcollars.common.PetCollarsMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler 
{
	public static String NETTY_VERSION = Integer.toString(1);
	
	public static Predicate<String> version = s -> NETTY_VERSION.equals(s);
	
	public static SimpleChannel CHANNEL_PET_COLLARS = NetworkRegistry.newSimpleChannel(new ResourceLocation(PetCollarsMain.MODID, "network"), () -> NETTY_VERSION, version, version);
	private static int ID =0;
	
	public static int getNextID()
	{
		return ID++;
	}
	
	public static void init()
	{
		CHANNEL_PET_COLLARS.registerMessage(getNextID(), MessageRequestCollarInfo.class, MessageRequestCollarInfo::encode, MessageRequestCollarInfo::decode, MessageRequestCollarInfo::conume);
		CHANNEL_PET_COLLARS.registerMessage(getNextID(), MessageResponseCollarInfo.class, MessageResponseCollarInfo::encode, MessageResponseCollarInfo::decode, MessageResponseCollarInfo::conume);
		

	}

}
