package mcenderdragon.petcollars.jei;

import java.util.Arrays;

import mcenderdragon.petcollars.common.CollarRecipeManager;
import mcenderdragon.petcollars.common.PetCollarsMain;
import mcenderdragon.petcollars.common.item.ItemList;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class PetCollarsJEI implements IModPlugin
{
	public static final ISubtypeInterpreter COLLAR_SUBTYPES = new ISubtypeInterpreter()
	{
		@Override
		public String apply(ItemStack itemStack) 
		{
			CompoundNBT nbt = itemStack.getChildTag("pendants");
			return nbt == null ? ISubtypeInterpreter.NONE : nbt.toString();
		}
	};
	
	public static final ResourceLocation COLLAR_CRAFTER_CATEGORY = new ResourceLocation(PetCollarsMain.MODID, "collar_crafter");
	
	
	@Override
	public ResourceLocation getPluginUid() 
	{
		return new ResourceLocation(PetCollarsMain.MODID, "jei");
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) 
	{
		registration.registerSubtypeInterpreter(ItemList.leather_collar, COLLAR_SUBTYPES);
		registration.registerSubtypeInterpreter(ItemList.gold_collar, COLLAR_SUBTYPES);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) 
	{
		registration.addRecipeCatalyst(new ItemStack(PetCollarsMain.collar_crafter), COLLAR_CRAFTER_CATEGORY);
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) 
	{
		System.out.println(Arrays.deepToString(CollarRecipeManager.getAllRecipes().toArray()));
		registration.addRecipes(CollarRecipeManager.getAllRecipes(), COLLAR_CRAFTER_CATEGORY);
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) 
	{
		registration.addRecipeCategories(new CollarCrafterCategory(registration.getJeiHelpers().getGuiHelper()));
	}
}
