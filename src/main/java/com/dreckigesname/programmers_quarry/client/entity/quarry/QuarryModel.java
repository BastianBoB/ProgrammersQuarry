package com.dreckigesname.programmers_quarry.client.entity.quarry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class QuarryModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("programmers_quarry", "quarry"), "main");
    private final ModelPart main;

    public QuarryModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, -6.0F, 10.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(14, 24).addBox(-7.0F, -6.0F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(26, 24).addBox(-5.0F, -11.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-3.0F, -13.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-4.0F, -12.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(1.0F, -12.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 32).addBox(-3.0F, -12.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(-3.0F, -12.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-3.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(15, 42).addBox(1.0F, -11.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 39).addBox(1.0F, -11.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 39).addBox(-4.0F, -11.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 39).addBox(-4.0F, -11.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -11.0F, 2.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-8.0F, -5.0F, -4.0F, 14.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(46, 25).addBox(-7.0F, -3.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 22).addBox(-4.0F, -3.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(37, 45).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(31, 45).addBox(2.0F, -3.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 44).addBox(2.0F, -3.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 19).addBox(-7.0F, -3.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 44).addBox(-4.0F, -3.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 39).addBox(-1.0F, -3.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 36).addBox(-7.0F, -4.0F, -6.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-7.0F, -1.0F, -6.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-8.0F, -3.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 37).addBox(4.0F, -3.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(4.0F, -3.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(34, 33).addBox(-7.0F, -1.0F, 4.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 13).addBox(-7.0F, -4.0F, 4.0F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 31).addBox(-8.0F, -3.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 6).addBox(4.0F, -5.0F, -6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 3).addBox(5.0F, -4.0F, -6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 43).addBox(5.0F, -4.0F, 4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(4.0F, -5.0F, 4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(6.0F, -3.0F, -6.0F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(4.0F, -6.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(3.0F, -10.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(5.0F, -11.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 24).addBox(6.0F, -11.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(6, 7).addBox(7.0F, -10.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 43).addBox(5.0F, -11.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 43).addBox(5.0F, -11.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 39).addBox(5.0F, -12.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 37).addBox(5.0F, -7.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}

