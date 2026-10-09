package destiny.secretsofthevoid.client.render;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.entity.PowerArmorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PowerArmorRenderer extends GeoEntityRenderer<PowerArmorEntity>
{
    public PowerArmorRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new DefaultedEntityGeoModel<>(new ResourceLocation(SecretsOfTheVoid.MODID, "test")));
    }


}
