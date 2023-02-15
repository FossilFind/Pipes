package org.duckdns.fossilfind.pipes.item;

import org.duckdns.fossilfind.pipes.Pipes;
import org.duckdns.fossilfind.pipes.block.PipesBlocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PipesItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Pipes.MODID);
	
	public static final RegistryObject<Item> ITEM_PIPE = ITEMS.register("item_pipe", () -> new BlockItem(PipesBlocks.ITEM_PIPE.get(), new Item.Properties()));
}