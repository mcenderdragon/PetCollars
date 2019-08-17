package mcenderdragon.petcollars.client.rendering;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;

public class SheepCollarRenderer extends GeneralCollarRenderer
{
	public SheepCollarRenderer()
	{
		super(SheepEntity.class, 0F, -10/16F, -10/16F, 0.74F, 0.74F, 0.74F);
	}

	@Override
	public void renderCollar(ClientCollarState collar, AnimalEntity animal, double x, double y, double z, float partialTicks)
	{
		if(((SheepEntity)animal).getSheared())
		{
			z += 1.8F/16F;
		}
		super.renderCollar(collar, animal, x, y, z, partialTicks);
	}
	

}
