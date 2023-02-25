package org.duckdns.fossilfind.pipes.pipe;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityNetwork<T>
{
	private List<LazyOptional<T>> capabilities;
	
	private CapabilityNetwork(List<LazyOptional<T>> capabilities)
	{
		this.capabilities = capabilities;
	}
	
	public static <C> CapabilityNetwork<C> forCap(Capability<C> cap)
	{
		return new CapabilityNetwork<C>(new ArrayList<LazyOptional<C>>());
	}
	
	public @NotNull LazyOptional<T> getCapability(int index)
	{
		return capabilities.get(index);
	}
	
	public void addToNetwork(BlockEntity entity, Direction direction)
	{
		capabilities.add(entity.getCapability(CapabilityManager.get(new CapabilityToken<T>(){}), direction));
	}
}