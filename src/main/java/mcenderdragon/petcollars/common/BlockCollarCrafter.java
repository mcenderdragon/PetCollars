package mcenderdragon.petcollars.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockCollarCrafter extends HorizontalBlock
{

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
		return new TileEntityCollarCrafter;
	}
}
