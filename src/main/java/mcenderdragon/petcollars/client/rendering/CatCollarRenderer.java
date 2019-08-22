package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import mcenderdragon.petcollars.client.ICollarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.item.ItemStack;

public class CatCollarRenderer implements ICollarRenderer<CatEntity>
{

	@Override
	public void renderCollar(ClientCollarState collar, CatEntity animal, double x, double y, double z, float partialTicks) 
	{
		GlStateManager.pushMatrix();
		
		y -= 18F/16F;
		z -= 7.3F/16F;
		GlStateManager.translated(x, y, z);
		
		if(animal.isSitting())
		{
			GlStateManager.rotatef(45F, 1, 0, 0);
			GlStateManager.translated(0, 0.127, -0.161);
		}
		
		GlStateManager.scaled(0.51, 0.51, 0.51);
		ItemStack it = collar.getAsItem();
		Minecraft.getInstance().getItemRenderer().renderItem(it, animal, ItemCameraTransforms.TransformType.GROUND, false);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public Class<CatEntity> getRenderingClass() 
	{
		return CatEntity.class;
	}

}
