package destiny.secretsofthevoid.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import destiny.secretsofthevoid.client.render.RenderTypes;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.entity.SievertRodHookEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SievertRodHookEntityRenderer extends EntityRenderer<SievertRodHookEntity> {
    private static final ResourceLocation HOOK = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/sievert_hook.png");
    private static final ResourceLocation HOOK_EMISSIVE = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/entity/sievert_hook_emissive.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(HOOK);
    private static final RenderType RENDER_TYPE_EMISSIVE = RenderTypes.getEmissiveRenderType(HOOK_EMISSIVE);
    private static final double VIEW_BOBBING_SCALE = (double)960.0F;

    public SievertRodHookEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public void render(SievertRodHookEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        Player player = pEntity.getPlayerOwner();
        if (player != null) {
            pMatrixStack.pushPose();
            pMatrixStack.pushPose();
            pMatrixStack.scale(0.5F, 0.5F, 0.5F);
            pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            PoseStack.Pose posestack$pose = pMatrixStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            VertexConsumer vertexconsumer = pBuffer.getBuffer(RENDER_TYPE);
            vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 0, 0, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 0, 1, 1);
            vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 1, 1, 0);
            vertex(vertexconsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 1, 0, 0);

            VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RENDER_TYPE_EMISSIVE);
            vertexEmissive(vertexconsumer1, matrix4f, matrix3f, 0.0F, 0, 0, 1);
            vertexEmissive(vertexconsumer1, matrix4f, matrix3f, 1.0F, 0, 1, 1);
            vertexEmissive(vertexconsumer1, matrix4f, matrix3f, 1.0F, 1, 1, 0);
            vertexEmissive(vertexconsumer1, matrix4f, matrix3f, 0.0F, 1, 0, 0);

            pMatrixStack.popPose();
            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.canPerformAction(ToolActions.FISHING_ROD_CAST)) {
                i = -i;
            }

            float f = player.getAttackAnim(pPartialTicks);
            float f1 = Mth.sin(Mth.sqrt(f) * (float)Math.PI);
            float f2 = Mth.lerp(pPartialTicks, player.yBodyRotO, player.yBodyRot) * ((float)Math.PI / 180F);
            double d0 = (double)Mth.sin(f2);
            double d1 = (double)Mth.cos(f2);
            double d2 = (double)i * 0.35;
            double d3 = 0.8;
            double d4;
            double d5;
            double d6;
            float f3;
            if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && player == Minecraft.getInstance().player) {
                double d7 = (double)960.0F / (double)(Integer)this.entityRenderDispatcher.options.fov().get();
                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.1F);
                vec3 = vec3.scale(d7);
                vec3 = vec3.yRot(f1 * 0.5F);
                vec3 = vec3.xRot(-f1 * 0.7F);
                d4 = Mth.lerp((double)pPartialTicks, player.xo, player.getX()) + vec3.x;
                d5 = Mth.lerp((double)pPartialTicks, player.yo, player.getY()) + vec3.y;
                d6 = Mth.lerp((double)pPartialTicks, player.zo, player.getZ()) + vec3.z;
                f3 = player.getEyeHeight();
            } else {
                d4 = Mth.lerp((double)pPartialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.8;
                d5 = player.yo + (double)player.getEyeHeight() + (player.getY() - player.yo) * (double)pPartialTicks - 0.45;
                d6 = Mth.lerp((double)pPartialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8;
                f3 = player.isCrouching() ? -0.1875F : 0.0F;
            }

            double d9 = Mth.lerp((double)pPartialTicks, pEntity.xo, pEntity.getX());
            double d10 = Mth.lerp((double)pPartialTicks, pEntity.yo, pEntity.getY()) + (double)0.25F;
            double d8 = Mth.lerp((double)pPartialTicks, pEntity.zo, pEntity.getZ());
            float f4 = (float)(d4 - d9);
            float f5 = (float)(d5 - d10) + f3;
            float f6 = (float)(d6 - d8);
            VertexConsumer vertexconsumer2 = pBuffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose posestack$pose1 = pMatrixStack.last();
            int j = 16;

            for(int k = 0; k <= 16; ++k) {
                stringVertex(f4, f5, f6, vertexconsumer2, posestack$pose1, fraction(k, 16), fraction(k + 1, 16));
            }

            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }

    }

    private static float fraction(int pNumerator, int pDenominator) {
        return (float)pNumerator / (float)pDenominator;
    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f p_254085_, Matrix3f p_253962_, int pLightmapUV, float pX, int pY, int pU, int pV) {
        pConsumer.vertex(p_254085_, pX - 0.5F, (float)pY - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)pU, (float)pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pLightmapUV).normal(p_253962_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void vertexEmissive(VertexConsumer pConsumer, Matrix4f p_254085_, Matrix3f p_253962_, float pX, int pY, int pU, int pV) {
        pConsumer.vertex(p_254085_, pX - 0.5F, (float)pY - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)pU, (float)pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(p_253962_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float p_174119_, float p_174120_, float p_174121_, VertexConsumer pConsumer, PoseStack.Pose pPose, float p_174124_, float p_174125_) {
        float f = p_174119_ * p_174124_;
        float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = p_174121_ * p_174124_;
        float f3 = p_174119_ * p_174125_ - f;
        float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = p_174121_ * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        pConsumer.vertex(pPose.pose(), f, f1, f2).color(0, 255, 0, 255).normal(pPose.normal(), f3, f4, f5).endVertex();
    }

    public ResourceLocation getTextureLocation(SievertRodHookEntity pEntity) {
        return HOOK;
    }
}
