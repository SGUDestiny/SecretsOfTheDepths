package destiny.secretsofthevoid.client.models;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.items.diving_gear.NetheriteGearItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NetheriteGearModel extends GeoModel<NetheriteGearItem> {
    @Override
    public ResourceLocation getModelResource(NetheriteGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "geo/armor/netherite_diving_gear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NetheriteGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "textures/item/netherite_diving_gear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NetheriteGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "animations/netherite_diving_gear.animation.json");
    }

    @Override
    public RenderType getRenderType(NetheriteGearItem animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
