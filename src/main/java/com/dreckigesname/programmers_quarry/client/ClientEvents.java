package com.dreckigesname.programmers_quarry.client;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.client.entity.quarry.QuarryModel;
import com.dreckigesname.programmers_quarry.client.entity.quarry.QuarryRenderer;
import com.dreckigesname.programmers_quarry.client.entity.quarry.RabRenderer;
import com.dreckigesname.programmers_quarry.common.screens.quarry_screen.QuarryScreen;
import com.dreckigesname.programmers_quarry.core.init.EntityInit;
import com.dreckigesname.programmers_quarry.core.init.MenuTypeInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


public final class ClientEvents {

    @EventBusSubscriber(modid = ProgrammersQuarry.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
    public static final class MOD{

        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.QUARRY_MENU.get(), QuarryScreen::new);
        }

        @SubscribeEvent
        public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(QuarryRenderer.QUARRY_LAYER, QuarryModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityInit.QUARRY.get(), QuarryRenderer::new);
            //event.registerEntityRenderer(EntityType.HORSE, RabRenderer::new);
        }
    }

    @EventBusSubscriber(modid = ProgrammersQuarry.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
    public static final class FORGE{

        @SubscribeEvent
        public static void clientTickEvent(final TickEvent.ClientTickEvent event){
            LocalPlayer player = Minecraft.getInstance().player;
        }
    }
}