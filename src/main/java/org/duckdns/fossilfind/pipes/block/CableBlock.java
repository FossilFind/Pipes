package org.duckdns.fossilfind.pipes.block;

import org.duckdns.fossilfind.pipes.block.entity.CableBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		var shape = Shapes.create(new AABB(7, 0, 7, 8, 16, 8));
		shape = Shapes.joinUnoptimized(shape, Shapes.create(new AABB(7, 7, 0, 8, 8, 16)), BooleanOp.OR);
		shape = Shapes.joinUnoptimized(shape, Shapes.create(new AABB(0, 7, 7, 16, 8, 8)), BooleanOp.OR);
		
		return shape.optimize();
	}
}