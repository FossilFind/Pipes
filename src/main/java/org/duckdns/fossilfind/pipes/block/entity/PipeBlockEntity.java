package org.duckdns.fossilfind.pipes.block.entity;

import org.duckdns.fossilfind.pipes.Pipes;
import org.duckdns.fossilfind.pipes.pipe.Connection;
import org.duckdns.fossilfind.pipes.util.DirectionalArray;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class PipeBlockEntity extends BlockEntity
{
	public static final ModelProperty<DirectionalArray<Connection>> CONNECTIONS = new ModelProperty<>();
	
	private DirectionalArray<Connection> connections;
	
	public PipeBlockEntity(BlockPos pos, BlockState blockState)
	{
		super(PipesBlockEntities.PIPE_BLOCK_ENTITY.get(), pos, blockState);
		
		connections = new DirectionalArray<>();
		connections.setValue(Direction.UP, new Connection()
		{
			@Override
			public ResourceLocation getModelLocation()
			{
				return new ResourceLocation(Pipes.MODID, "block/pipe_connection");
			}
		});
		connections.setValue(Direction.NORTH, new Connection()
		{
			@Override
			public ResourceLocation getModelLocation()
			{
				return new ResourceLocation(Pipes.MODID, "block/pipe_connection");
			}
		});
		
		requestModelDataUpdate();
	}
	
	@Override
	public @NotNull ModelData getModelData()
	{
		return ModelData.builder()
				.with(CONNECTIONS, connections)
				.build();
	}
}