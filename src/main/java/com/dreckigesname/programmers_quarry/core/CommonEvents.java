package com.dreckigesname.programmers_quarry.core;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.packets.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonEvents {

    @EventBusSubscriber(modid = ProgrammersQuarry.MOD_ID, bus = Bus.MOD)
    public static final class ModEvents {

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            PacketHandler.init();
        }
    }
}
