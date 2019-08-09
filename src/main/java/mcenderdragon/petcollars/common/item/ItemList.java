package mcenderdragon.petcollars.common.item;

import java.util.function.Consumer;

import mcenderdragon.petcollars.common.PetCollarsMain;
import mcenderdragon.petcollars.common.pendant.PendantList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class ItemList
{
	public static final Item leather_collar = new ItemCollarBase(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1), 1).setRegistryName(PetCollarsMain.MODID, "leather_collar");
	
	public static final Item dummy_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC), PendantList.dummy).setRegistryName(PetCollarsMain.MODID, "dummy_pendant");
	
	public static void init(Consumer<Item> register)
	{
		register.accept(leather_collar);
		register.accept(dummy_pendant);
	}
}
