package mcenderdragon.petcollars.common.pendant;

import java.util.function.Consumer;

import mcenderdragon.petcollars.common.PetCollarsMain;

public class PendantList 
{
	public static final PendantBase<?> dummy = new DummyPendant().setRegistryName(PetCollarsMain.MODID, "dummy");
	public static final PendantBase<?> heat_resistance_pendant = new FireResistancePendant(30, 0x4e86d7, 50).setRegistryName(PetCollarsMain.MODID, "heat_resistance_pendant");
	public static final PendantBase<?> fire_insensitive_pendant = new FireResistancePendant(30, 0xE63B0B, 50).setRegistryName(PetCollarsMain.MODID, "fire_insensitive_pendant");
	public static final PendantBase<?> fire_resistance_pendant = new FireResistancePendant(20, 0xD12111, 75).setRegistryName(PetCollarsMain.MODID, "fire_resistance_pendant");
	public static final PendantBase<?> fire_immunity_pendant = new FireResistancePendant(6, 0xFF0000, 125).setRegistryName(PetCollarsMain.MODID, "fire_immunity_pendant");
	
	
	public static void init(Consumer<PendantBase<?>> register)
	{
		register.accept(dummy);
		register.accept(heat_resistance_pendant);
		register.accept(fire_insensitive_pendant);
		register.accept(fire_resistance_pendant);
		register.accept(fire_immunity_pendant);
	}
}
