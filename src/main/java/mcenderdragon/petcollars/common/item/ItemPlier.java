package mcenderdragon.petcollars.common.item;

import mcenderdragon.petcollars.common.HelperCollars;
import mcenderdragon.petcollars.common.collar.CollarCapProvider;
import mcenderdragon.petcollars.common.collar.ICollar;
import mcenderdragon.petcollars.network.MessageResponseCollarInfo;
import mcenderdragon.petcollars.network.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint;

public class ItemPlier extends Item 
{

	public ItemPlier(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity pl, LivingEntity target, Hand hand) 
	{
		if(target!=null && HelperCollars.hasCollar(target))
		{
			ICollar collar = CollarCapProvider.removeCollar((AnimalEntity) target);
			if(collar!=null)
			{
				if(pl.getEntityWorld().isRemote)
					return true;
			
				PacketHandler.CHANNEL_PET_COLLARS.send(PacketDistributor.NEAR.with(()-> new TargetPoint(target.posX, target.posY, target.posZ, 50F, target.getEntityWorld().dimension.getType())), new MessageResponseCollarInfo(target));
				
				
				ItemStack collarStack = HelperCollars.createCollarStack(collar);
				ItemEntity drop = new ItemEntity(pl.world, pl.posX, pl.posY, pl.posZ, collarStack);
				pl.world.addEntity(drop);
			}
		}
		
		return super.itemInteractionForEntity(stack, pl, target, hand);
	}
}
