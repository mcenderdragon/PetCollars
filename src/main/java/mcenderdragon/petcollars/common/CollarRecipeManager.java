package mcenderdragon.petcollars.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import mcenderdragon.petcollars.common.item.ItemCollarBase;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.common.pendant.PendantList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CollarRecipeManager 
{
	private static Thread runner;
	private static List<CollarRecipe> allRecipes;
	
	public static void init()
	{
		runner = new Thread(CollarRecipeManager::asyncRecipeCreation, "CollarRecipeManager");
		runner.start();
	}
	
	private static void asyncRecipeCreation()
	{
		allRecipes = PendantRegistry.getCollarList().parallelStream()
			.map(CollarRecipeManager::createCombinations)
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
		
		runner = null;	
	}
	
	@SuppressWarnings("unchecked")
	private static Collection<CollarRecipe> createCombinations(ItemCollarBase base)
	{
		PendantBase<INBTSerializable<CompoundNBT>>[] allPendants = PendantRegistry.PENDANT_REGISTRY.getValues().stream().filter(p -> p != PendantList.dummy).toArray(PendantBase[]::new);
		PendantBase<INBTSerializable<CompoundNBT>>[] finished = new PendantBase[base.pendantAmount];
		int[] pos = new int[base.pendantAmount];
		
		ArrayList<CollarRecipe> list = new ArrayList<>( (int)Math.pow(allPendants.length, pos.length));
		
		
		while(true)
		{
			for(int i=0;i<finished.length;i++)
				finished[i] = allPendants[pos[i]];
			
			list.add(new CollarRecipe(base, finished));
			
			int j = pos.length -1;
			while(j>=0)
			{
				pos[j]++;
				if(pos[j] >= allPendants.length)
				{
					pos[j] = 0;
					j--;
				}
				else
				{
					break;
				}
			}
			if(j<0)
			{
				break;
			}
				
		}
		
		return list;
	}
	
	public static Collection<CollarRecipe> getAllRecipes()
	{
		if(runner!=null && runner.isAlive())
		{
			try {
				runner.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return allRecipes;
	}
	
	public static class CollarRecipe
	{
		private ItemStack output;	
		private ItemStack collar;
		private ItemStack[] pendants;

		
		public CollarRecipe(ItemStack output, ItemStack collar, ItemStack[] pendants) 
		{
			super();
			this.output = output;
			this.collar = collar;
			this.pendants = pendants;
		}
		
		public CollarRecipe(ItemCollarBase base, PendantBase<INBTSerializable<CompoundNBT>>[] pendants) 
		{
			@SuppressWarnings("unchecked")
			INBTSerializable<CompoundNBT>[] data = new INBTSerializable[pendants.length];
			this.pendants = new ItemStack[pendants.length];
			for(int i=0;i<pendants.length;i++)
			{
				data[i] = pendants[i].deserialize(new CompoundNBT());
				this.pendants[i] = HelperCollars.createPendantStack(pendants[i], data[i]);
			}
			output = HelperCollars.createCollarStack(base, pendants, data);
			collar = new ItemStack(base);
		}

		public ItemStack getOutput() {
			return output;
		}

		public ItemStack getCollar() {
			return collar;
		}

		public ItemStack[] getPendants() {
			return pendants;
		}
		
		@Override
		public String toString() 
		{
			return String.format("%s + %s -> %s", collar, Arrays.toString(pendants), output);
		}
	}
}
