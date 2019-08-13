package mcenderdragon.petcollars.client;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import mcenderdragon.petcollars.client.CollarRenderHelper.ClientCollarState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;

public class CollarRenderManager 
{
	private static Map<Class<AnimalEntity>, ICollarRenderer<AnimalEntity>> renderMap = new IdentityHashMap<Class<AnimalEntity>, ICollarRenderer<AnimalEntity>>();
	
	private static final ICollarRenderer<AnimalEntity> FALLBACK = new ICollarRenderer<AnimalEntity>() 
	{
		@Override
		public Class<AnimalEntity> getRenderingClass() 
		{
			return AnimalEntity.class;
		}
	};
	
	public static void renderCollar(AnimalEntity entityIn, ClientCollarState st, float partialTicks) 
	{
		if(st.hasCollar())
		{
			getRenderer(entityIn.getClass()).renderCollar(st, entityIn, 0, 0, 0, partialTicks);
		}
	}
	
	
	public static void bindTexture(ResourceLocation res)
	{
		Minecraft.getInstance().getRenderManager().textureManager.bindTexture(res);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends AnimalEntity> void registerCollarRenderer(ICollarRenderer<T> renderer)
	{
		renderMap.put((Class<AnimalEntity>)renderer.getRenderingClass(), (ICollarRenderer<AnimalEntity>) renderer);
	}
	
	@SuppressWarnings("unchecked")
	@Nonnull
	public static ICollarRenderer<AnimalEntity> getRenderer(Class<?> cls)
	{
		ICollarRenderer<AnimalEntity> renderer = renderMap.getOrDefault(cls, null);
		if(renderer!=null)
		{
			return renderer;
		}
		else
		{
			if(cls != AnimalEntity.class)
			{
				renderer = getRenderer(cls.getSuperclass());
				if(renderer!=null)
				{
					renderMap.put((Class<AnimalEntity>) cls, renderer);
					return renderer;
				}
			}
		}
		return FALLBACK;
	}
 	
}
