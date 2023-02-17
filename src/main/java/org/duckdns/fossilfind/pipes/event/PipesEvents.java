package org.duckdns.fossilfind.pipes.event;

import java.util.ArrayList;

import org.duckdns.fossilfind.pipes.block.entity.CableBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class PipesEvents
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
		
		if(level.getBlockEntity(pos) instanceof CableBlockEntity be)
		{
			ArrayList<AABB> boxes = new ArrayList<>();
			
			boxes.add(new AABB(7 / 16d, 0 / 16d, 7 / 16d, 8 / 16d, 8 / 16d, 8 / 16d));
			
			for(AABB box : boxes)
			{
				LevelRenderer.renderVoxelShape(poseStack, buffers.getBuffer(RenderType.lines()), Shapes.create(box), pos.getX() - camera.getPosition().x, pos.getY() - camera.getPosition().y, pos.getZ() - camera.getPosition().z, 0, 0, 0, 0.4f);
			}
			
			event.setCanceled(true);
		}
	}
}