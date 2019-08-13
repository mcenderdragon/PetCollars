package mcenderdragon.petcollars.client;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public interface ICollarRenderer<T extends AnimalEntity>
{
	public default void renderCollar(ClientCollarState collar, T animal, double x, double y, double z, float partialTicks)
	{
		y += animal.getEyeHeight() * 0.5F;
		
		GlStateManager.pushMatrix();
		
		GlStateManager.translated(x, y, z);
		GlStateManager.scaled(1.1, 1.1, 1.1);
		
		GlStateManager.rotatef((animal.ticksExisted+partialTicks)*2, 0, 1, 0);
		
		
		
		ItemStack it = collar.getAsItem();
		
		Minecraft.getInstance().getItemRenderer().renderItem(it, animal, ItemCameraTransforms.TransformType.GROUND, false);
		
		GlStateManager.popMatrix();
	}
	
	public Class<T> getRenderingClass();
}
