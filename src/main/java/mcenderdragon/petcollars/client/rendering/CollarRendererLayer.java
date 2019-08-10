package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.CollarRenderHelper;
import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import mcenderdragon.petcollars.client.CollarRenderManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.passive.AnimalEntity;

public class CollarRendererLayer extends LayerRenderer<AnimalEntity, EntityModel<AnimalEntity>> 
{

	public CollarRendererLayer(IEntityRenderer<AnimalEntity, EntityModel<AnimalEntity>> entityRendererIn) 
	{
		super(entityRendererIn);
	}

	@Override
	public void render(AnimalEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) 
	{
		
		ClientCollarState st = CollarRenderHelper.getCollarState(entityIn);
		if(st == CollarRenderHelper.UNKNOWN)
		{
			System.out.println("Packet for " + entityIn + " has been send");
		}
		else
		{
			GlStateManager.pushMatrix();
			GlStateManager.rotatef(180F, 0, 0, 1);
			CollarRenderManager.renderCollar(entityIn, st, partialTicks);
			GlStateManager.popMatrix();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		
		return false;
	}

}
