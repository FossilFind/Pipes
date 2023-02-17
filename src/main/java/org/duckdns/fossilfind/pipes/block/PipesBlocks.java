package org.duckdns.fossilfind.pipes.block;

import org.duckdns.fossilfind.pipes.Pipes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PipesBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pipes.MODID);
	
	public static final RegistryObject<Block> CABLE = BLOCKS.register("cable", () -> new CableBlock(BlockBehaviour.Properties.of(Material.METAL)));
}