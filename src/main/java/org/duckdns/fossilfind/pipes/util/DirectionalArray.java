package org.duckdns.fossilfind.pipes.util;

import java.util.function.BiConsumer;

import net.minecraft.core.Direction;

public class DirectionalArray<T>
{
	@SuppressWarnings("unchecked")
	private final T[] values = (T[]) new Object[6];
	
	public T getValue(Direction dir)
	{
		return values[dir.ordinal()];
	}
	
	public void setValue(Direction dir, T value)
	{
		values[dir.ordinal()] = value;
	}
	
	public void removeValue(Direction dir)
	{
		values[dir.ordinal()] = null;
	}
	
	public boolean hasValue(Direction dir)
	{
		return values[dir.ordinal()] == null;
	}
	
	public boolean hasValue(T value)
	{
		for(T t : values)
			if(t == values)
				return true;
		
		return false;
	}
	
	public Direction getDirectionOfValue(T value)
	{
		if(!hasValue(value))
			return null;
		
		for(int i = 0; i < values.length; i++)
			if(values[i] == values)
				return Direction.values()[i];
		
		return null;
	}
	
	public T[] values()
	{
		return values;
	}
	
	public void forEach(BiConsumer<Direction, T> action)
	{
		for(int i = 0; i < values.length; i++)
			action.accept(Direction.values()[i], values[i]);
	}
	
	public void replace(DirectionalArray<T> from)
	{
		for(int i = 0; i < values.length; i++)
			values[i] = from.values[i];
	}
}