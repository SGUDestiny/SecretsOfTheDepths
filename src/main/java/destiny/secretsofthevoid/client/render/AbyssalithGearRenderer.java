package destiny.secretsofthevoid.client.render;

import destiny.secretsofthevoid.client.models.AbyssalithGearModel;
import destiny.secretsofthevoid.server.items.diving_gear.AbyssalithGearItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AbyssalithGearRenderer extends GeoArmorRenderer<AbyssalithGearItem> {
    public AbyssalithGearRenderer() {
        super(new AbyssalithGearModel());
    }

    @Override
    public RenderType getRenderType(AbyssalithGearItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick)
    {
        return RenderType.entityTranslucent(texture);
    }
}
