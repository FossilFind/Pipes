package org.duckdns.fossilfind.pipes;

import org.duckdns.fossilfind.pipes.block.PipesBlocks;
import org.duckdns.fossilfind.pipes.block.entity.PipesBlockEntityTypes;
import org.duckdns.fossilfind.pipes.item.PipesItems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Pipes.MODID)
public class Pipes
{
	public static final String MODID = "pipes";
	
	public Pipes()
	{
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		PipesBlocks.BLOCKS.register(bus);
		PipesItems.ITEMS.register(bus);
		PipesBlockEntityTypes.BLOCK_ENTITIES.register(bus);
	}
}