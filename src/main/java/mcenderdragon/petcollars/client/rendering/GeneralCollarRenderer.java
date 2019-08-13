package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import mcenderdragon.petcollars.client.ICollarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;

public class GeneralCollarRenderer implements ICollarRenderer<AnimalEntity> 
{
	private Class<? extends AnimalEntity> renderingClass;
	private float dx,dy,dz;
	private float scaleX,scaleY,scaleZ;
	
	
	public GeneralCollarRenderer(Class<? extends AnimalEntity> renderingClass, float dx, float dy, float dz, float scaleX, float scaleY, float scaleZ) 
	{
		super();
		this.renderingClass = renderingClass;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}

	@Override
	public void renderCollar(ClientCollarState collar, AnimalEntity animal, double x, double y, double z, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translated(x+dx, y+dy, z+dz);
		GlStateManager.scaled(scaleX, scaleY, scaleZ);
		
		
		ItemStack it = collar.getAsItem();
		Minecraft.getInstance().getItemRenderer().renderItem(it, animal, ItemCameraTransforms.TransformType.GROUND, false);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public Class<AnimalEntity> getRenderingClass() 
	{
		return (Class<AnimalEntity>) renderingClass;
	}

}
