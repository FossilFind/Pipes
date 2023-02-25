package org.duckdns.fossilfind.pipes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PipeBlockEntity extends BlockEntity
{
	public PipeBlockEntity(BlockPos pos, BlockState blockState)
	{
		super(PipesBlockEntityTypes.PIPE_BLOCK_ENTITY.get(), pos, blockState);
	}
}