package org.duckdns.fossilfind.pipes.block;

import java.util.HashMap;
import java.util.Map;

import org.duckdns.fossilfind.pipes.block.entity.PipeBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class PipeBlock extends BaseEntityBlock
{
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	
	private static final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();
	
	public final Capability<?> capability;
	
	public PipeBlock(Capability<?> capability)
	{
		super(Properties.of(Material.METAL).noOcclusion());
		
		this.capability = capability;
		
		registerDefaultState(stateDefinition.any()
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(UP, false)
				.setValue(DOWN, false));
	}
	
	public static BooleanProperty getConnectionProperty(Direction direction)
	{
		switch(direction)
		{
		case NORTH:
			return NORTH;
		case EAST:
			return EAST;
		case SOUTH:
			return SOUTH;
		case WEST:
			return WEST;
		case UP:
			return UP;
		case DOWN:
			return DOWN;
		default:
			return NORTH;
		}
	}
	
	public boolean connectsTo(BlockGetter level, BlockPos pos, Direction direction)
	{
		if(level.getBlockState(pos).getBlock() == PipesBlocks.PIPE.get())
			return true;
		
		BlockEntity be = level.getBlockEntity(pos);
		
		if(be != null)
			if(be.getCapability(capability, direction.getOpposite()).orElse(null) != null)
				return true;
		
		return false;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new PipeBlockEntity(pos, state);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
	{
		return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		BlockGetter level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		return super.getStateForPlacement(context)
				.setValue(NORTH, connectsTo(level, pos.north(), Direction.NORTH))
				.setValue(EAST, connectsTo(level, pos.east(), Direction.EAST))
				.setValue(SOUTH, connectsTo(level, pos.south(), Direction.SOUTH))
				.setValue(WEST, connectsTo(level, pos.west(), Direction.WEST))
				.setValue(UP, connectsTo(level, pos.above(), Direction.UP))
				.setValue(DOWN, connectsTo(level, pos.below(), Direction.DOWN));
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos)
	{
		return state.setValue(getConnectionProperty(direction), connectsTo(level, neighborPos, direction));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return getPipeShape(state);
	}
	
	private static VoxelShape getPipeShape(BlockState state)
	{
		return SHAPES.computeIfAbsent(state, (s) ->
		{
			VoxelShape shape = Block.box(7, 7, 7, 9, 9, 9);
			
			if(s.getValue(NORTH))
				shape = Shapes.or(shape, Block.box(7, 7, 0, 9, 9, 7));

			if(s.getValue(EAST))
				shape = Shapes.or(shape, Block.box(9, 7, 7, 16, 9, 9));

			if(s.getValue(SOUTH))
				shape = Shapes.or(shape, Block.box(7, 7, 9, 9, 9, 16));

			if(s.getValue(WEST))
				shape = Shapes.or(shape, Block.box(0, 7, 7, 7, 9, 9));

			if(s.getValue(UP))
				shape = Shapes.or(shape, Block.box(7, 9, 7, 9, 16, 9));

			if(s.getValue(DOWN))
				shape = Shapes.or(shape, Block.box(7, 0, 7, 9, 7, 9));			
			
			return shape;
		});
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos)
	{
		return true;
	}
}