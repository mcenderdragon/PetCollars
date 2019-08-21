package mcenderdragon.petcollars.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;

public class BlockCollarCrafter extends HorizontalBlock
{
	public static VoxelShape shape = Block.makeCuboidShape(0, 0, 0, 16, 12, 16);

	protected BlockCollarCrafter(Properties builder) 
	{
		super(builder);
	}

	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new TileEntityCollarCrafter();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return shape;
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(!worldIn.isRemote)
		{
			TileEntityCollarCrafter crafter = (TileEntityCollarCrafter) worldIn.getTileEntity(pos);
			if(hit.getFace() == Direction.UP)
			{
				
				ItemStack held = crafter.addItem(player.getHeldItem(handIn));
				player.setHeldItem(handIn, held);
			}
			else
			{
				if(crafter.tryCraft())
				{
					((ServerWorld)worldIn).playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1F, 0.9F, false);
				}
				else
				{
					((ServerWorld)worldIn).playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1F, 0.9F, false);
				}
			}
			return true;
		}
		return true;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(HorizontalBlock.HORIZONTAL_FACING);
		super.fillStateContainer(builder);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return super.getStateForPlacement(context).with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}
}
