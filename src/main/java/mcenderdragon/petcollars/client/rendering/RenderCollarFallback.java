package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderCollarFallback extends EntityRenderer<AnimalEntity>
{

	protected RenderCollarFallback(EntityRendererManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(AnimalEntity entity, double x, double y, double z, float entityYaw, float partialTicks) 
	{
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		
	}

	
	
	@Override
	protected ResourceLocation getEntityTexture(AnimalEntity entity) 
	{
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
	
}
