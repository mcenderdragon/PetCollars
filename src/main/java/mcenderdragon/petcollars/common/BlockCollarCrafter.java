package mcenderdragon.petcollars.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
	public boolean onBlockActivated(BlockState state, World w, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(!w.isRemote)
		{
			TileEntityCollarCrafter crafter = (TileEntityCollarCrafter) w.getTileEntity(pos);
			if(hit.getFace() == Direction.UP)
			{				
				ItemStack held = player.getHeldItem(handIn);
				ItemStack heldNew = crafter.addItem(held);
				
				if(held == heldNew)
				{
					//item could not get inserted
					Vec3d hitVec = hit.getHitVec().add(-pos.getX(), -pos.getY(), -pos.getZ());
					double x = hitVec.x - 0.5;
					double z = hitVec.z - 0.5;
					
					double o;
					//rotation correction
					switch (state.get(HORIZONTAL_FACING))
					{
					case SOUTH://DONE
						x =  -x;
						z =  -z;
						break;
					case EAST://Done
						o = x;
						x = z;
						z = -o;
						break;
					case WEST://DONE
						o = x;
						x = -z;
						z = o;
						break;
					default:
						break;
					}
					
					x += 0.5;
					z += 0.5;
					
//					((ServerWorld)w).spawnParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT, x + pos.getX(), pos.getY()+hitVec.y, pos.getZ()+z, 1, 0,0,0,0); 
					
					if(x>6F/16F && z<10F/16F && z > 1F/16F && z < 5F/16F)
					{
						crafter.tryRemoveFromSlot(0);
					}
					else if(z > 8F/16F && z < 10F/16F)
					{
						if(x > 1F/16F && x < 3F/16F)
						{
							crafter.tryRemoveFromSlot(4);
						}
						else if(x > 13F/16F && x < 15/16F)
						{
							crafter.tryRemoveFromSlot(5);
						}
					}
					else if(z > 12/16F && z < 14/16F)
					{
						if(x > 3F/16F && x < 5F/16F)
						{
							crafter.tryRemoveFromSlot(2);
						}
						else if(x > 7F/16F && x < 9/16F)
						{
							crafter.tryRemoveFromSlot(1);
						}
						else if(x > 11F/16F && x < 13/16F)
						{
							crafter.tryRemoveFromSlot(3);
						}
					}
				}
				else
				{
					player.setHeldItem(handIn, heldNew);
				}
				
			}
			else
			{
				if(crafter.tryCraft())
				{
					((ServerWorld)w).playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1F, 0.9F);
				}
				else
				{
					((ServerWorld)w).playSound(null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1F, 1.9F);
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
