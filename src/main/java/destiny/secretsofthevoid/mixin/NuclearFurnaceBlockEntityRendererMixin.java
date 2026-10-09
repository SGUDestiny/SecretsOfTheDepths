package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.render.ACRenderTypes;
import com.github.alexmodguy.alexscaves.client.render.blockentity.NuclearFurnaceBlockRenderer;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.registry.BlockRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NuclearFurnaceBlockRenderer.class)
public class NuclearFurnaceBlockEntityRendererMixin {
    @Unique
    private static final ResourceLocation OFF_TEXTURE = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_off.png");
    @Unique
    private static final ResourceLocation ON_TEXTURE = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_on.png");
    @Unique
    private static final ResourceLocation SUBCRITICAL_TEXTURE = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_subcritical.png");
    @Unique
    private static final ResourceLocation CRITICAL_TEXTURE = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_critical.png");
    @Unique
    private static final ResourceLocation SUPERCRITICAL_TEXTURE = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_supercritical.png");
    @Unique
    private static final ResourceLocation OFF_TEXTURE_GLOW = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_off_glow.png");
    @Unique
    private static final ResourceLocation ON_TEXTURE_GLOW = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_on_glow.png");
    @Unique
    private static final ResourceLocation SUBCRITICAL_TEXTURE_GLOW = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_subcritical_glow.png");
    @Unique
    private static final ResourceLocation CRITICAL_TEXTURE_GLOW = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_critical_glow.png");
    @Unique
    private static final ResourceLocation SUPERCRITICAL_TEXTURE_GLOW = new ResourceLocation("alexscaves", "textures/entity/nuclear_furnace/nuclear_furnace_supercritical_glow.png");

    @Unique
    private static final ResourceLocation ON_TEXTURE_GAMMA = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/nuclear_furnace/nuclear_furnace_on_gamma.png");
    @Unique
    private static final ResourceLocation ON_TEXTURE_GLOW_GAMMA = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/nuclear_furnace/nuclear_furnace_on_glow_gamma.png");
    @Unique
    private static final ResourceLocation SUBCRITICAL_TEXTURE_GAMMA = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/nuclear_furnace/nuclear_furnace_subcritical_gamma.png");
    @Unique
    private static final ResourceLocation SUBCRITICAL_TEXTURE_GLOW_GAMMA = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/nuclear_furnace/nuclear_furnace_subcritical_glow_gamma.png");

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    private RenderType getRenderTypeFor(NuclearFurnaceBlockEntity furnace, boolean glow) {
        if (!furnace.isUndergoingFission() && furnace.getCriticality() <= 0) {
            return glow ? ACRenderTypes.getEyesAlphaEnabled(OFF_TEXTURE_GLOW) : RenderType.entityCutoutNoCull(OFF_TEXTURE);
        } else if (furnace.getCriticality() == 1) {
            if (furnace.getItem(1).getItem() == BlockRegistry.GAMMA_ROD.get().asItem()) {
                return glow ? ACRenderTypes.getEyesAlphaEnabled(SUBCRITICAL_TEXTURE_GLOW_GAMMA) : RenderType.entityCutoutNoCull(SUBCRITICAL_TEXTURE_GAMMA);
            }

            return glow ? ACRenderTypes.getEyesAlphaEnabled(SUBCRITICAL_TEXTURE_GLOW) : RenderType.entityCutoutNoCull(SUBCRITICAL_TEXTURE);
        } else if (furnace.getCriticality() == 2) {
            return glow ? ACRenderTypes.getEyesAlphaEnabled(CRITICAL_TEXTURE_GLOW) : RenderType.entityCutoutNoCull(CRITICAL_TEXTURE);
        } else if (furnace.getCriticality() >= 3) {
            return glow ? ACRenderTypes.getEyesAlphaEnabled(SUPERCRITICAL_TEXTURE_GLOW) : RenderType.entityCutoutNoCull(SUPERCRITICAL_TEXTURE);
        } else {
            if (furnace.getItem(1).getItem() == BlockRegistry.GAMMA_ROD.get().asItem()) {
                return glow ? ACRenderTypes.getEyesAlphaEnabled(ON_TEXTURE_GLOW_GAMMA) : RenderType.entityCutoutNoCull(ON_TEXTURE_GAMMA);
            }

            return glow ? ACRenderTypes.getEyesAlphaEnabled(ON_TEXTURE_GLOW) : RenderType.entityCutoutNoCull(ON_TEXTURE);
        }
    }
}
