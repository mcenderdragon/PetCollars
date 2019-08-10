package mcenderdragon.petcollars.common.item;

import java.util.Arrays;
import java.util.List;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.PendantRegistry;
import mcenderdragon.petcollars.common.collar.DynamicCollarInstance;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.common.pendant.DummyAdditionalInfo;
import mcenderdragon.petcollars.common.pendant.PendantBase;
import mcenderdragon.petcollars.common.pendant.PendantList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemCollarBase extends Item
{
	public final int pendantAmount;

	public ItemCollarBase(Properties properties, int pendantAmount) 
	{
		super(properties);
		this.pendantAmount = pendantAmount;
		this.addPropertyOverride(new ResourceLocation("pendant"), new IItemPropertyGetter()
		{
			@Override
			public float call(ItemStack st, World w, LivingEntity living) 
			{
				CompoundNBT nbt = st.getChildTag("pendants");
				if(nbt != null)
				{
					if(nbt.contains("p0"))
					{
						return 1;
					}
				}
				return 0;
			}
		});
	}

	public ICollar createCollar(CompoundNBT nbt, AnimalEntity animal, PendantBase<INBTSerializable<CompoundNBT>>[] pendants, INBTSerializable<CompoundNBT>[] data)
	{
		if(pendants.length > pendantAmount)
			throw new IllegalArgumentException(String.format("This Collar can only support %s pendants, but %s where given", pendantAmount, pendants.length));
		
		return new DynamicCollarInstance(data, pendants, animal, this);
	}
	
	public ICollar createCollar(ItemStack stack, AnimalEntity animal)
	{
		CompoundNBT nbt = stack.getChildTag("pendants");
		if(nbt != null)
		{
			PendantBase<INBTSerializable<CompoundNBT>>[] pendants = HelperCollars.loadPendants(nbt);
			INBTSerializable<CompoundNBT>[] data = HelperCollars.loadAdditionalInfo(nbt, pendants);
			
			return new DynamicCollarInstance(data, pendants, animal, this);
		}
		else
		{
			PendantBase<INBTSerializable<CompoundNBT>>[] pendants = new PendantBase[pendantAmount];
			INBTSerializable<CompoundNBT>[] data = new INBTSerializable[pendantAmount];
			Arrays.fill(pendants, null);
			Arrays.fill(data, null);
			
			return new DynamicCollarInstance(data, pendants, animal, this);
		}
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) 
	{
		if(!playerIn.world.isRemote)
		{
			if(HelperCollars.hasCollar(target))
			{
				return false;
			}
			else if(target instanceof AnimalEntity)
			{
				HelperCollars.attachCollar(stack, this, (AnimalEntity)target);
				stack.shrink(1);
				return true;
			}
			return super.itemInteractionForEntity(stack, playerIn, target, hand);
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) 
	{
		if (this.isInGroup(group)) 
		{
			items.add(new ItemStack(this));
			 
			for(PendantBase<?> pendant : PendantRegistry.PENDANT_REGISTRY.getValues())
			{
				if(pendant != PendantList.dummy)
				{ 
					PendantBase<INBTSerializable<CompoundNBT>>[] pendants = new PendantBase[pendantAmount];
					INBTSerializable<CompoundNBT>[] data = new INBTSerializable[pendantAmount];
					Arrays.fill(pendants, pendant);
					Arrays.fill(data, pendants[0].deserialize(new CompoundNBT()));
					 
					ItemStack item = HelperCollars.createCollarStack(this, pendants, data);
					items.add(item);
				}	
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
	{
		CompoundNBT nbt = stack.getChildTag("pendants");
		if(nbt != null)
		{
			PendantBase<INBTSerializable<CompoundNBT>>[] pendants = HelperCollars.loadPendants(nbt);
			tooltip.add(new TranslationTextComponent("item.collar.pendants"));
			for(PendantBase<INBTSerializable<CompoundNBT>> pendant : pendants)
			{
				tooltip.add(pendant.getName());
			}
			
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
