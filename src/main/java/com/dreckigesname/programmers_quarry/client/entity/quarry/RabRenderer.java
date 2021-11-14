package com.dreckigesname.programmers_quarry.client.entity.quarry;

import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Horse;

public class RabRenderer extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {

    public RabRenderer(EntityRendererProvider.Context p_174360_) {
        super(p_174360_, new HorseModel<>(p_174360_.bakeLayer(ModelLayers.HORSE)), 1.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(Horse p_114482_) {
        return null;
    }
}
