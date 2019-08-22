package mcenderdragon.petcollars.common.pendant;

import mcenderdragon.petcollars.common.collar.ICollar;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;

public class DummyPendant extends PendantBase<DummyAdditionalInfo> 
{

	@Override
	public DummyAdditionalInfo deserialize(CompoundNBT nbt) 
	{
		return DummyAdditionalInfo.DUMMY;
	}

	@Override
	public void onAnimalAttackedBy(AnimalEntity animal, DamageSource src, float rawDamage, ICollar collar, DummyAdditionalInfo customInfo) 
	{
		((ServerWorld)animal.world).spawnParticle(ParticleTypes.LARGE_SMOKE, animal.posX, animal.posY+animal.getEyeHeight(), animal.posZ, 10, 0, 0, 0, 0);
		
		super.onAnimalAttackedBy(animal, src, rawDamage, collar, customInfo);
	}
}
