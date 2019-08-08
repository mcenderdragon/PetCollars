package mcenderdragon.petcollars.common.collar;

import java.util.UUID;

import mcenderdragon.petcollars.common.PetCollarsMain;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CollarCapProvider implements ICapabilityProvider, INBTSerializable<INBT>
{
	public final AnimalEntity animal;
	
	private LazyOptional<ICollar> opt;
	
	private DynamicCollarInstance deserializedInstance;
	
	public CollarCapProvider(AnimalEntity animal) 
	{
		this.animal = animal;
		opt = LazyOptional.of(this::createCollar);
		opt.addListener(p -> opt = null);
	}
	
	private ICollar createCollar()
	{
		if(deserializedInstance!=null)
			return deserializedInstance;
		
		return new 
				
				
		/* AAAAAAA
		 * I made a big error here
		 * the capabilities are added to every animal as if its already collared
		 * but thats not the case
		 * so I need to make something like a collar-inventory-capability for every animal
		 * :( 
		 */
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
	{
		if(cap == PetCollarsMain.COLLAR)
		{
			if(opt!=null)
				return (LazyOptional<T>) opt;
			else
			{
				opt = LazyOptional.of(this::createCollar);
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
				
				PendantBase<INBTSerializable<CompoundNBT>>[] pendants = col.gettAllPendants();
				
			}
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(INBT nbt) 
	{
		// TODO Auto-generated method stub
		
	}

	public void invalidate() 
	{
		if(opt!=null)
			opt.invalidate();
	}

}
