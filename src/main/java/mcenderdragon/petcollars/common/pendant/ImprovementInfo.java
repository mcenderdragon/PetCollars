package mcenderdragon.petcollars.common.pendant;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ImprovementInfo implements  INBTSerializable<CompoundNBT>
{
	private int armorLvl=0, attackLvl=0;
	private float damageDealt, damageTaken;
	
	protected boolean needsUpdate = true;
	
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
			needsUpdate = true;
		}
	}

	public void addAttackDamage(float amount)
	{
		damageDealt += amount;
		if(damageDealt > neededDamageDealt())
		{
			damageDealt -= neededDamageDealt();
			attackLvl++;
			needsUpdate = true;
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
	
	private AttributeModifier armor_base = new AttributeModifier(UUID.fromString("d8109a0f-9bdb-4b16-9deb-5840120316da"), this::getArmorName, 1.0, AttributeModifier.Operation.ADDITION);
	private AttributeModifier health_base = new AttributeModifier(UUID.fromString("67b4609a-86f3-11ea-bc55-0242ac130003"), this::getHealthName, 4.0, AttributeModifier.Operation.ADDITION);
	
	protected AttributeModifier getArmorModifier()
	{
		return armor_base;
	}
	
	protected AttributeModifier getHealthModifier()
	{
		return armor_base;
	}

	public String getArmorName() 
	{
		return "improve.pendant.armor "+armorLvl;
	}
	
	public String getHealthName() 
	{
		return "improve.pendant.health "+armorLvl;
	}
}
