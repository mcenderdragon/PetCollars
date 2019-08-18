package mcenderdragon.petcollars.common.item;

import java.util.function.Consumer;

import mcenderdragon.petcollars.common.PetCollarsMain;
import mcenderdragon.petcollars.common.pendant.PendantList;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;

public class ItemList
{
	public static final Item leather_collar = new ItemCollarBase(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1), 1).setRegistryName(PetCollarsMain.MODID, "leather_collar");
	
	public static final Item dummy_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC), PendantList.dummy).setRegistryName(PetCollarsMain.MODID, "dummy_pendant");
	public static final Item heat_resistance_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.COMMON), PendantList.heat_resistance_pendant).setRegistryName(PetCollarsMain.MODID, "heat_resistance_pendant");
	public static final Item fire_insensitive_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON), PendantList.fire_insensitive_pendant).setRegistryName(PetCollarsMain.MODID, "fire_insensitive_pendant");
	public static final Item fire_resistance_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE), PendantList.fire_resistance_pendant).setRegistryName(PetCollarsMain.MODID, "fire_resistance_pendant");
	public static final Item fire_immunity_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC), PendantList.fire_immunity_pendant).setRegistryName(PetCollarsMain.MODID, "fire_immunity_pendant");
	public static final Item improvement_pendant = new ItemPendantBase(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.COMMON), PendantList.improvement_pendant).setRegistryName(PetCollarsMain.MODID, "improvement_pendant");
	
	
	public static void init(Consumer<Item> register)
	{
		register.accept(leather_collar);
		register.accept(dummy_pendant);
		register.accept(heat_resistance_pendant);
		register.accept(fire_insensitive_pendant);
		register.accept(fire_resistance_pendant);
		register.accept(fire_immunity_pendant);
		register.accept(improvement_pendant);
		
		register.accept(new BlockItem(PetCollarsMain.collar_crafter, new Item.Properties().group(ItemGroup.REDSTONE)).setRegistryName(PetCollarsMain.collar_crafter.getRegistryName()));
	}
}
