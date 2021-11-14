package com.dreckigesname.programmers_quarry.core.init;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.menus.QuarryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuTypeInit {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ProgrammersQuarry.MOD_ID);

    public static final RegistryObject<MenuType<QuarryMenu>> QUARRY_MENU = MENU_TYPES.register("quarry_menu",
            () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.level;
        return new QuarryMenu(windowId, world, pos, inv, inv.player);
    }));
}
