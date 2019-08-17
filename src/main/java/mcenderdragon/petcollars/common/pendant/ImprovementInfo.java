package mcenderdragon.petcollars.common.pendant;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ImprovementInfo implements  INBTSerializable<CompoundNBT>
{
	private int armorLvl=0, attackLvl=0;
	private float damageDealt, damageTaken;
	
	
	public ImprovementInfo(CompoundNBT nbt) 
	{
		deserializeNBT(nbt);
	}

	@Override
	public CompoundNBT serializeNBT() 
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("armorL", armorLvl);
		nbt.putInt("attackL", attackLvl);
		nbt.putFloat("dealt", damageDealt);
		nbt.putFloat("taken", damageTaken);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) 
	{
		armorLvl = nbt.getInt("armorL");
		attackLvl = nbt.getInt("attackL");
		damageDealt = nbt.getFloat("dealt");
		damageTaken = nbt.getFloat("taken");
	}

	public void addDamage(float damage) 
	{
		damageTaken += damage;
		if(damageTaken > neededDamageTaken())
		{
			damageTaken -= neededDamageTaken();
			armorLvl++;
		}
	}

	public void addAttackDamage(float amount)
	{
		damageDealt += amount;
		if(damageDealt > neededDamageDealt())
		{
			damageDealt -= neededDamageDealt();
			attackLvl++;
		}
	}

	public float neededDamageDealt()
	{
		return (float) (20F * Math.pow(2, attackLvl));
	}
	
	public float neededDamageTaken()
	{
		return (float) (10F * Math.pow(2, armorLvl));
	}
	
	public int getArmorLvl()
	{
		return armorLvl;
	}
	
	public int getAttackLvl()
	{
		return attackLvl;
	}
}
