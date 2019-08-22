package mcenderdragon.petcollars.jei;

import java.util.ArrayList;

import mcenderdragon.petcollars.common.CollarRecipeManager.CollarRecipe;
import mcenderdragon.petcollars.common.PetCollarsMain;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CollarCrafterCategory implements IRecipeCategory<CollarRecipe> 
{
	private IDrawable background, icon, slotDrawable;

	public CollarCrafterCategory(IGuiHelper gui) 
	{
		super();
		
		background = gui.createBlankDrawable(72, 72);
		
		icon = gui.createDrawableIngredient(new ItemStack(PetCollarsMain.collar_crafter));
		slotDrawable = gui.getSlotDrawable();
	}

	@Override
	public ResourceLocation getUid() 
	{
		return PetCollarsJEI.COLLAR_CRAFTER_CATEGORY;
	}

	@Override
	public Class<? extends CollarRecipe> getRecipeClass() 
	{
		return CollarRecipe.class;
	}

	@Override
	public String getTitle() 
	{
		return "Collar Crafting";
	}

	@Override
	public IDrawable getBackground() 
	{
		return background;
	}

	@Override
	public IDrawable getIcon() 
	{
		return icon;
	}

	@Override
	public void setIngredients(CollarRecipe recipe, IIngredients ingredients) 
	{
		ArrayList<ItemStack> l = new ArrayList<>(recipe.getPendants().length +1);
		l.add(recipe.getCollar());
		for(ItemStack it : recipe.getPendants())
			l.add(it);
		
		ingredients.setInputs(VanillaTypes.ITEM, l);
		
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CollarRecipe recipe, IIngredients ingredients) 
	{
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(0, true, 28, 7);
		
		itemStacks.init(1, true, 28, 55);
		itemStacks.init(2, true, 10, 55);
		itemStacks.init(3, true, 46, 55);
		itemStacks.init(4, true, 1 , 37);
		itemStacks.init(5, true, 55, 37);
		
		itemStacks.init(6, false, 28, 28);

		itemStacks.set(ingredients);
	}
	
	@Override
	public void draw(CollarRecipe recipe, double mouseX, double mouseY) 
	{
		slotDrawable.draw(28, 7);
		
		slotDrawable.draw(28, 55);
		slotDrawable.draw(10, 55);
		slotDrawable.draw(45, 55);
		slotDrawable.draw(1 , 37);
		slotDrawable.draw(54, 37);
	}

}
