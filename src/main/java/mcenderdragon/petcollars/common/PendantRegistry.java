package mcenderdragon.petcollars.common;

import javax.annotation.Nullable;

import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

public class PendantRegistry 
{
	public static final ResourceLocation PENDANTS_NAME = new ResourceLocation(PetCollarsMain.MODID, "pendants");
	
	// return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(MAX_VARINT).addCallback(new NamespacedDefaultedWrapper.Factory<T>()).setDefaultKey(_default);
	@SuppressWarnings("unchecked")
	public static final IForgeRegistry<PendantBase<?>> PENDANT_REGISTRY = makeRegistry(PENDANTS_NAME, PendantBase.class).addCallback(PendantCallbacks.INSTANCE).create();
	
	private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type)
    {
        return new RegistryBuilder<T>().setName(name).setType(type).setMaxID(Short.MAX_VALUE);
    }
	
	public static void init()
	{
		PENDANT_REGISTRY.isEmpty();
	}
	
	 private static class PendantCallbacks implements IForgeRegistry.AddCallback<PendantBase<?>>, IForgeRegistry.ClearCallback<PendantBase<?>>, IForgeRegistry.BakeCallback<PendantBase<?>>, IForgeRegistry.CreateCallback<PendantBase<?>>
	 {
	        static final PendantCallbacks INSTANCE = new PendantCallbacks();

	        @Override
	        public void onAdd(IForgeRegistryInternal<PendantBase<?>> owner, RegistryManager stage, int id, PendantBase<?> block, @Nullable PendantBase<?> oldBlock)
	        {
	            
	        }

	        @Override
	        public void onClear(IForgeRegistryInternal<PendantBase<?>> owner, RegistryManager stage)
	        {
	           
	        }

	        @Override
	        public void onCreate(IForgeRegistryInternal<PendantBase<?>> owner, RegistryManager stage)
	        {
	            
	        }

	        @Override
	        public void onBake(IForgeRegistryInternal<PendantBase<?>> owner, RegistryManager stage)
	        {
	            
	        }

	       
	    }
}
