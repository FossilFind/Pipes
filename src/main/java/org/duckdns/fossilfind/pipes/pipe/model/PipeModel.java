package org.duckdns.fossilfind.pipes.pipe.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.duckdns.fossilfind.pipes.Pipes;
import org.duckdns.fossilfind.pipes.pipe.Connection;
import org.duckdns.fossilfind.pipes.util.DirectionalArray;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.IQuadTransformer;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.common.util.ConcatenatedListView;

public class PipeModel implements IUnbakedGeometry<PipeModel>
{
	private final BlockModel base;
	private final DirectionalArray<Connection> sideParts;
	
	public PipeModel(BlockModel base, DirectionalArray<Connection> sideParts)
	{
		this.base = base;
		this.sideParts = sideParts;
	}
	
	@Override
	public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		return new Baked(base, sideParts, baker, spriteGetter, modelState, overrides, modelLocation);
	}
	
	public static class Baked implements IDynamicBakedModel
	{
		private static final Map<ResourceLocation, BakedModel> modelCache = new HashMap<>();
		
		private final BakedModel base;
		private final DirectionalArray<Connection> sideParts;
		
		private ModelBaker baker;
		private Function<Material, TextureAtlasSprite> spriteGetter;
		private ModelState modelState;
		private ItemOverrides overrides;
		private ResourceLocation modelLocation;
		
		public Baked(BlockModel base, DirectionalArray<Connection> sideParts, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation)
		{
			this.base = base.bake(baker, base, spriteGetter, modelState, modelLocation, false);
			this.sideParts = sideParts;
			
			this.baker = baker;
			this.spriteGetter = spriteGetter;
			this.modelState = modelState;
			this.overrides = overrides;
			this.modelLocation = modelLocation;
		}
		
		@Override
		public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType)
		{
			List<List<BakedQuad>> quads = new ArrayList<>();
			
			//sideParts.replace(extraData.get(PipeBlockEntity.CONNECTIONS));
			
			quads.add(base.getQuads(state, side, rand, extraData, renderType));
			
			sideParts.forEach((dir, part) ->
			{
				if(part == null)
					return;
				
				ResourceLocation location = part.getModelLocation();
				BakedModel model;
				
				if(modelCache.containsKey(location))
					model = modelCache.get(location);
				else
					model = Minecraft.getInstance().getModelManager().getModelBakery().getModel(location).bake(baker, spriteGetter, modelState, modelLocation);
				
				Transformation transform = new Transformation(new Matrix4f().translate(0.5f, 0.5f, 0.5f));
				transform = transform.compose(new Transformation(dir.getRotation().get(new Matrix4f())));
				transform = transform.compose(new Transformation(new Matrix4f().translate(-0.5f, 0.0625f, -0.5f)));
				
				IQuadTransformer transformer = QuadTransformers.applying(transform);
				
				quads.add(transformer.process(model.getQuads(state, side, rand, extraData, renderType)));
			});
			
			return ConcatenatedListView.of(quads);
		}
		
		@Override
		public boolean useAmbientOcclusion()
		{
			return true;
		}
		
		@Override
		public boolean isGui3d()
		{
			return false;
		}
		
		@Override
		public boolean usesBlockLight()
		{
			return false;
		}
		
		@Override
		public boolean isCustomRenderer()
		{
			return false;
		}
		
		@Override
		public TextureAtlasSprite getParticleIcon()
		{
			return spriteGetter.apply(ForgeHooksClient.getBlockMaterial(new ResourceLocation("block/black_concrete")));
		}
		
		@Override
		public ItemOverrides getOverrides()
		{
			return overrides;
		}
	}
	
	public static final class Loader implements IGeometryLoader<PipeModel>
	{
		public static final Loader INSTANCE = new Loader();
		public static final ResourceLocation ID = new ResourceLocation(Pipes.MODID, "pipe_model");
		
		private Loader() { }
		
		@Override
		public PipeModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException
		{
			jsonObject.remove("loader");
			
			return new PipeModel(BlockModel.fromString(jsonObject.toString()), new DirectionalArray<Connection>());
		}
	}
}