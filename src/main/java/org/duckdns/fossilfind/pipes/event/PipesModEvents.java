package org.duckdns.fossilfind.pipes.event;

import org.duckdns.fossilfind.pipes.pipe.model.PipeModel;

import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD)
public class PipesModEvents
{
	@SubscribeEvent
	public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event)
	{
		event.register(PipeModel.Loader.ID.getPath(), PipeModel.Loader.INSTANCE);
	}
}