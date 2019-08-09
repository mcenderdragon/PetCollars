package mcenderdragon.petcollars.client.rendering;

import mcenderdragon.petcollars.client.CollarRenderHelper;
import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
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
	public void render(AnimalEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) 
	{
		ClientCollarState st = CollarRenderHelper.getCollarState(entityIn);
		if(st == CollarRenderHelper.UNKNOWN)
		{
			System.out.println("Packet for " + entityIn + " has been send");
		}
			
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		
		return false;
	}

}
