package mcenderdragon.petcollars.common.collar;

import java.util.ArrayList;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractCollarInstance implements ICollar
{
	private final INBTSerializable<CompoundNBT>[] additionalInfo;
	private final PendantBase<INBTSerializable<CompoundNBT>>[] pendants;
	private final AnimalEntity animalFromCollar;
	
	
	private PendantBase<INBTSerializable<CompoundNBT>>[] needUpdates;
	private int[] needUpdatesArrayPositions;
	private int[] waitTime;	
	
	
	
	public AbstractCollarInstance(INBTSerializable<CompoundNBT>[] additionalInfo, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, AnimalEntity animalFromCollar)
	{
		super();
		this.additionalInfo = additionalInfo;
		this.pendants = pendants;
		this.animalFromCollar = animalFromCollar;
	}

	public void update()
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] needUpdates = getTickablePendants();
		
		
		for(int i=0;i<needUpdates.length;i++)
		{
			if(waitTime[i] <= 0)
			{
				needUpdates[i].update(animalFromCollar, this, additionalInfo[needUpdatesArrayPositions[i]]);
				waitTime[i] = needUpdates[i].getUpdateFrequenzy();
			}
			else
			{
				waitTime[i]--;
			}
		}
	}
	
	public PendantBase<INBTSerializable<CompoundNBT>>[] getTickablePendants()
	{
		if(needUpdates==null)
		{
			ArrayList<PendantBase<INBTSerializable<CompoundNBT>>> ticking = new ArrayList<PendantBase<INBTSerializable<CompoundNBT>>>(pendants.length);
			ArrayList<Integer> poses = new ArrayList<>(pendants.length);
			for(int i=0;i<pendants.length;i++)
			{
				if(pendants[i].needsUpdate())
				{
					ticking.add(pendants[i]);
					poses.add(i);
				}
			}
			needUpdatesArrayPositions = new int[poses.size()];
			for(int i=0;i<needUpdatesArrayPositions.length;i++)
			{
				needUpdatesArrayPositions[i] = poses.get(i);
			}
			
			waitTime = new int[ticking.size()];
			return needUpdates = ticking.toArray(new PendantBase[ticking.size()]);
		}
		else
		{
			return needUpdates;
		}
	}
	
	@Override
	public INBTSerializable<CompoundNBT>[] getAllAdditionalInfo() 
	{
		return additionalInfo;
	}
	
	@Override
	public PendantBase<INBTSerializable<CompoundNBT>>[] gettAllPendants() 
	{
		return pendants;
	}
	
	@Override
	public AnimalEntity getAnimal() 
	{
		return animalFromCollar;
	}
}
