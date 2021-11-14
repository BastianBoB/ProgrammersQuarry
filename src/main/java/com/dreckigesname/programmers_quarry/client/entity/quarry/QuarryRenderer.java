package com.dreckigesname.programmers_quarry.client.entity.quarry;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.entities.QuarryEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class QuarryRenderer extends EntityRenderer<QuarryEntity> {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ProgrammersQuarry.MOD_ID, "textures/entities/quarry.png");
    public static final ModelLayerLocation QUARRY_LAYER = QuarryModel.LAYER_LOCATION;
    private final QuarryModel<QuarryEntity> model;

    public QuarryRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new QuarryModel<>(context.bakeLayer(QUARRY_LAYER));
    }

    @Override
    public void render(QuarryEntity quarryEntity, float idk, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int idkEither) {
        System.out.print("yRot0: " + quarryEntity.yRotO);
        System.out.println("  yRot: " + quarryEntity.getYRot());
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        poseStack.scale(-1, -1, 1);
        poseStack.translate(0, -1.5, 0);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180f + quarryEntity.getViewYRot(partialTicks)));

        this.model.renderToBuffer(poseStack, vertexConsumer, idkEither, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(quarryEntity, idk, partialTicks, poseStack, bufferSource, idkEither);
    }

    @Override
    public ResourceLocation getTextureLocation(QuarryEntity p_114482_) {
        return TEXTURE_LOCATION;
    }

}
