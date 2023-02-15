package org.duckdns.fossilfind.pipes.block;

import org.duckdns.fossilfind.pipes.Pipes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PipesBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pipes.MODID);
	
	public static final RegistryObject<Block> ITEM_PIPE = BLOCKS.register("item_pipe", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
}