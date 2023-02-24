package org.duckdns.fossilfind.pipes.block.entity;

import org.duckdns.fossilfind.pipes.Pipes;
import org.duckdns.fossilfind.pipes.block.PipesBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PipesBlockEntities
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pipes.MODID);
	
	public static final RegistryObject<BlockEntityType<PipeBlockEntity>> PIPE_BLOCK_ENTITY = BLOCK_ENTITIES.register("pipe", () -> BlockEntityType.Builder.of(PipeBlockEntity::new, PipesBlocks.PIPE.get()).build(null));
}