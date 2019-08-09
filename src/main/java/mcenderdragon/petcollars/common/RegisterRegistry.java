package mcenderdragon.petcollars.common;

import mcenderdragon.petcollars.common.item.ItemList;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.common.pendant.PendantList;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = PetCollarsMain.MODID, bus = Bus.MOD)
public class RegisterRegistry 
{
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
	{
		ItemList.init(event.getRegistry()::register);
	}

	@SubscribeEvent
    public static void registerPendants(RegistryEvent.Register<PendantBase<?>> event)
	{
		PendantList.init(event.getRegistry()::register);
	}
}
