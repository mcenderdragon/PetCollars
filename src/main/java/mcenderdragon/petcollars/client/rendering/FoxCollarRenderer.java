package mcenderdragon.petcollars.client.rendering;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;

public class FoxCollarRenderer extends GeneralCollarRenderer 
{

	public FoxCollarRenderer() 
	{
		super(FoxEntity.class, 0F, 1F, 0F, 0.5F, 0.5F, 0.5F);
	}
	
	@Override
	public void renderCollar(ClientCollarState collar, AnimalEntity animal, double x, double y, double z, float partialTicks) 
	{
		scaleX = scaleY = scaleZ = 0.74F;
		
		dx = 0F;
		dy = -21/16F;
		dz = -2/16F;
		
		if( ((FoxEntity)animal).isSleeping() )
		{
			dy = -23/16F;
			dx = -1/16F;
		}
		else if( ((FoxEntity)animal).isSitting() )
		{
			dy = -16/16F;
			dz = -3.5F/16F;
		}

		
		super.renderCollar(collar, animal, x, y, z, partialTicks);
	}

}
