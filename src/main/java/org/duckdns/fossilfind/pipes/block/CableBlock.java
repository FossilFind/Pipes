package org.duckdns.fossilfind.pipes.block;

import org.duckdns.fossilfind.pipes.block.entity.CableBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlock extends BaseEntityBlock
{
	public CableBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new CableBlockEntity(null, pos, state);
	}
	
//	@Override
//	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
//	{
//		var shape = Shapes.box(7 / 16d, 0 / 16d, 7 / 16d, 9 / 16d, 16 / 16d, 9 / 16d);
//		shape = Shapes.join(shape, Shapes.box(7 / 16d, 7 / 16d, 0 / 16d, 9 / 16d, 9 / 16d, 16 / 16d), BooleanOp.OR);
//		shape = Shapes.join(shape, Shapes.box(0 / 16d, 7 / 16d, 7 / 16d, 16 / 16d, 9 / 16d, 9 / 16d), BooleanOp.OR);
//		
//		return shape;
//	}
}