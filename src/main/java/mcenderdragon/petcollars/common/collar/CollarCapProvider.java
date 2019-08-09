package mcenderdragon.petcollars.common.collar;

import java.util.UUID;
import java.util.WeakHashMap;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.PetCollarsMain;
import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

public class CollarCapProvider implements ICapabilityProvider, INBTSerializable<INBT>
{
	private static final WeakHashMap<AnimalEntity, CollarCapProvider> animalProviderMap = new WeakHashMap<AnimalEntity, CollarCapProvider>();
	
	public final AnimalEntity animal;
	
	private LazyOptional<ICollar> opt;
	
	private ICollar deserializedInstance;
	
	public CollarCapProvider(AnimalEntity animal) 
	{
		this.animal = animal;
		animalProviderMap.put(animal, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		if(cap == PetCollarsMain.COLLAR)
		{
			if(opt!=null)
				return (LazyOptional<T>) opt;
			else if(deserializedInstance!=null)
			{
				opt = LazyOptional.of(() -> deserializedInstance);
				opt.addListener(p -> opt = null);
				return (LazyOptional<T>) opt;
			}
		}
		return LazyOptional.empty();
	}

	@Override
	public INBT serializeNBT() 
	{
		CompoundNBT nbt = new CompoundNBT();
		if(opt!=null)
		{
			if(opt.isPresent())
			{
				ICollar col = opt.orElseThrow(NullPointerException::new);
				UUID uuid = col.getAnimal().getUniqueID();
				nbt.putUniqueId("animalID", uuid);
				
				nbt.putString("collar", col.asItem().getRegistryName().toString());
				
				PendantBase<INBTSerializable<CompoundNBT>>[] pendants = col.gettAllPendants();
				INBTSerializable<CompoundNBT>[] moreInfo = col.getAllAdditionalInfo();
				
				HelperCollars.saveToNBT(nbt, pendants, moreInfo);
			}
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(INBT inbt) 
	{
		if(inbt instanceof CompoundNBT)
		{
			CompoundNBT nbt = (CompoundNBT) inbt;
			UUID current = animal.getUniqueID();
			
			if(nbt.hasUniqueId("animalID"))
			{
				UUID storedId = nbt.getUniqueId("animalID");
				if(current.equals(storedId))
				{
					PendantBase<INBTSerializable<CompoundNBT>>[] pendants = HelperCollars.loadPendants(nbt);
					INBTSerializable<CompoundNBT>[] data = HelperCollars.loadAdditionalInfo(nbt, pendants);
					
					ItemCollarBase collar = (ItemCollarBase) ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("collar")));
					if(collar!=null)
					{
						deserializedInstance = collar.createCollar(nbt, animal, pendants, data);
					}
				}
				else
				{
					throw new IllegalStateException("UUID mismatch:" + current + " != " + storedId);
				}
			}
		}
	}

	private void initCollar(ICollar col)
	{
		if(deserializedInstance!=null)
			throw new IllegalArgumentException("Already has a collar");
		else
		{
			deserializedInstance = col;
			opt = LazyOptional.of(() -> deserializedInstance);
			opt.addListener(p -> opt = null);
		}
	}
	
	public void invalidate() 
	{
		if(opt!=null)
			opt.invalidate();
	}

	public static void addCollar(ICollar collar, AnimalEntity animal)
	{
		CollarCapProvider provider = animalProviderMap.get(animal);
		if(provider!=null)
		{
			provider.initCollar(collar);
		}
	}
}
