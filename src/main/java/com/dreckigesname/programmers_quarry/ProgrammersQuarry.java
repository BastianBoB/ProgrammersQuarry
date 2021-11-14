package com.dreckigesname.programmers_quarry;

import com.dreckigesname.programmers_quarry.core.init.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

@Mod(ProgrammersQuarry.MOD_ID)
public class ProgrammersQuarry {

    public static final String MOD_ID = "programmers_quarry";
    public static final CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.BLACK_WOOL);
        }
    };


    public ProgrammersQuarry() {
        Configurator.setLevel("net.minecraft.commands", Level.DEBUG);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityInit.ENTITIES.register(bus);
        BlockEntityTypeInit.BLOCK_ENTITY_TYPES.register(bus);
        MenuTypeInit.MENU_TYPES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
