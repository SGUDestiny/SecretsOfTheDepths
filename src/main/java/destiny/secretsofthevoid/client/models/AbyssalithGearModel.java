package destiny.secretsofthevoid.client.models;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.items.diving_gear.AbyssalithGearItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbyssalithGearModel extends GeoModel<AbyssalithGearItem> {
    @Override
    public ResourceLocation getModelResource(AbyssalithGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "geo/armor/abyssalith_diving_gear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbyssalithGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "textures/item/abyssalith_diving_gear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AbyssalithGearItem animatable) {
        return new ResourceLocation(SecretsOfTheVoid.MODID, "animations/abyssalith_diving_gear.animation.json");
    }

    @Override
    public RenderType getRenderType(AbyssalithGearItem animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
