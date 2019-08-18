package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.common.TileEntityCollarCrafter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;

public class TileCollarCrafterRenderer extends TileEntityRenderer<TileEntityCollarCrafter> 
{
	@Override
	public void render(TileEntityCollarCrafter tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translated(x+0.5, y+12.1/16F, z+0.5);
		IItemHandler handler = tileEntityIn.getHandler();
		
//		GlStateManager.enableCull();
		GlStateManager.rotatef(-90F, 1F, 0, 0);
		GlStateManager.scalef(0.25F, 0.25F, 0.25F);
		for(int i=0;i<handler.getSlots();i++)
		{
			GlStateManager.pushMatrix();
			ItemStack st = handler.getStackInSlot(i);
			
			if(!st.isEmpty())
			{
				if(i==0)
				{
					GlStateManager.translatef(0, 1F, 0);
					GlStateManager.scaled(2.0, 2.0, 2.0);
				}
				else if(i==1)
				{
					GlStateManager.translatef(0, -1.35F, 0);
				}
				else if(i==2)
				{
					GlStateManager.translatef(-1.0F, -1.35F, 0);
				}
				else if(i==3)
				{
					GlStateManager.translatef(1.0F, -1.35F, 0);
				}
				else if(i==4)
				{
					GlStateManager.translatef(1.5F, -0.35F, 0);
				}
				else if(i==5)
				{
					GlStateManager.translatef(-1.5F, -0.35F, 0);
				}
				
//				GlStateManager.translated(0.5, 0, 0);
				ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
				ir.renderItem(st, EntityType.COW.create(tileEntityIn.getWorld()), ItemCameraTransforms.TransformType.GROUND, false);
//				IBakedModel ibakedmodel = ir.getModelWithOverrides(st, tileEntityIn.getWorld(), null);
//				ir.renderItemModel(st, ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
				
			}
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();
		super.render(tileEntityIn, x, y, z, partialTicks, destroyStage);
	}
}
