package mcenderdragon.petcollars.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;

public class HorseCollarRenderer extends GeneralCollarRenderer 
{

	public HorseCollarRenderer() 
	{
		super(AbstractHorseEntity.class, 0F, -7/16F, -9/16F, 1F, 1F, 1F);
	}

	@Override
	public void renderCollar(ClientCollarState collar, AnimalEntity animal, double x, double y, double z, float partialTicks) 
	{
		dz = -12.3F/16F;
		dy = -9.5F/16F;
		
		scaleY = 1.3F;
		
		if( ((AbstractHorseEntity)animal).isRearing())
		{
			GlStateManager.rotatef(45f, 1F, 0, 0);
			dz = -6/16F;
			dy = -4.5F/16F;
		}
		
		super.renderCollar(collar, animal, x, y, z, partialTicks);
	}
}
