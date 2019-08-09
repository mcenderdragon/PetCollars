package mcenderdragon.petcollars.common.pendant;

import java.util.function.Consumer;

import mcenderdragon.petcollars.common.PetCollarsMain;

public class PendantList 
{
	public static final PendantBase<?> dummy = new DummyPendant().setRegistryName(PetCollarsMain.MODID, "dummy");
	public static final PendantBase<?> fire_resistance_pendant = new FireResistancePendant().setRegistryName(PetCollarsMain.MODID, "fire_resistance_pendant");
	
	
	public static void init(Consumer<PendantBase<?>> register)
	{
		register.accept(dummy);
		register.accept(fire_resistance_pendant);
	}
}
