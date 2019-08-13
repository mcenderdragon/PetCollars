package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.ICollarRenderer;
import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;

public class WolfCollarRenderer implements ICollarRenderer<WolfEntity> 
{

	@Override
	public void renderCollar(ClientCollarState collar, WolfEntity animal, double x, double y, double z, float partialTicks)
	{
		GlStateManager.pushMatrix();
		
		y -= 18F/16F;
		z -= 6.3F/16F;
		GlStateManager.translated(x, y, z);
		
		
		if(animal.isSitting())
		{
			GlStateManager.translated(0, 0, -0.058);
			GlStateManager.rotatef(19F, 1, 0, 0);
		}

		
		
		GlStateManager.scaled(1.01, 1.5, 1.01);
		ItemStack it = collar.getAsItem();
		Minecraft.getInstance().getItemRenderer().renderItem(it, animal, ItemCameraTransforms.TransformType.GROUND, false);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public Class<WolfEntity> getRenderingClass() 
	{
		return WolfEntity.class;
	}

}
