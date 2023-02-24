package org.duckdns.fossilfind.pipes.event;

import java.util.ArrayList;

import org.duckdns.fossilfind.pipes.block.entity.PipeBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class PipesForgeEvents
{
	@SubscribeEvent
	public static void onRenderHighlightEvent(RenderHighlightEvent.Block event)
	{
		@SuppressWarnings("resource")
		var level = Minecraft.getInstance().level;
		@SuppressWarnings("resource")
		var player = Minecraft.getInstance().level;
		var poseStack = event.getPoseStack();
		var buffers = event.getMultiBufferSource();
		var camera = event.getCamera();
		var hitResult = event.getTarget();
		
		if(level == null || player == null || buffers == null)
			return;
        
		if(hitResult.getType() != HitResult.Type.BLOCK)
			return;
		
		BlockPos pos = hitResult.getBlockPos();
		
		if(level.getBlockEntity(pos) instanceof PipeBlockEntity be)
		{
			BlockState state = be.getBlockState();
			
			var boxes = new ArrayList<AABB>();
			
			boxes.add(new AABB(7 / 16d, 7 / 16d, 7 / 16d, 9 / 16d, 9 / 16d, 9 / 16d));
			
			if(state.getValue(PipeBlock.NORTH))
				boxes.add(new AABB(7 / 16d, 7 / 16d, 0 / 16d, 9 / 16d, 9 / 16d, 7 / 16d));
			if(state.getValue(PipeBlock.EAST))
				boxes.add(new AABB(9 / 16d, 7 / 16d, 7 / 16d, 16 / 16d, 9 / 16d, 9 / 16d));
			if(state.getValue(PipeBlock.SOUTH))
				boxes.add(new AABB(7 / 16d, 7 / 16d, 9 / 16d, 9 / 16d, 9 / 16d, 16 / 16d));
			if(state.getValue(PipeBlock.WEST))
				boxes.add(new AABB(0 / 16d, 7 / 16d, 7 / 16d, 7 / 16d, 9 / 16d, 9 / 16d));
			if(state.getValue(PipeBlock.UP))
				boxes.add(new AABB(7 / 16d, 9 / 16d, 7 / 16d, 9 / 16d, 16 / 16d, 9 / 16d));
			if(state.getValue(PipeBlock.DOWN))
				boxes.add(new AABB(7 / 16d, 0 / 16d, 7 / 16d, 9 / 16d, 7 / 16d, 9 / 16d));
			
			for(AABB box : boxes)
			{
				if(box.inflate(0.01).contains(hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ())))
				{
					renderShape(poseStack, buffers.getBuffer(RenderType.lines()), Shapes.create(box), pos.getX() - camera.getPosition().x, pos.getY() - camera.getPosition().y, pos.getZ() - camera.getPosition().z, 0, 0, 0, 1);
					break;
				}
			}
			
			event.setCanceled(true);
		}
	}
	
	private static void renderShape(PoseStack poseStack, VertexConsumer consumer, VoxelShape shape, double camDistX, double camDistY, double camDistZ, float red, float green, float blue, float alpha)
	{
		shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) ->
		{
			float normX = (float)(maxX - minX);
			float normY = (float)(maxY - minY);
			float normZ = (float)(maxZ - minZ);
			
			float length = Mth.sqrt(normX * normX + normY * normY + normZ * normZ);
			
			normX /= length;
			normY /= length;
			normZ /= length;
			
			consumer.vertex(poseStack.last().pose(), (float)(minX + camDistX), (float)(minY + camDistY), (float)(minZ + camDistZ)).color(red, green, blue, alpha).normal(poseStack.last().normal(), normX, normY, normZ).endVertex();
			consumer.vertex(poseStack.last().pose(), (float)(maxX + camDistX), (float)(maxY + camDistY), (float)(maxZ + camDistZ)).color(red, green, blue, alpha).normal(poseStack.last().normal(), normX, normY, normZ).endVertex();
      });
   }
}