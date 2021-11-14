package com.dreckigesname.programmers_quarry.core.init;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.items.Wrench;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ProgrammersQuarry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {

    static final Set<RegistryObject<Block>> BLOCK_ITEM_BLACKLIST = new HashSet<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProgrammersQuarry.MOD_ID);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", Wrench::new);

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        BlockInit.BLOCKS.getEntries().stream().filter(object -> !BLOCK_ITEM_BLACKLIST.contains(object))
                .forEach(block -> {
                    final var blockItem = new BlockItem(block.get(), new Item.Properties().tab(ProgrammersQuarry.TAB));
                    blockItem.setRegistryName(block.getId());
                    registry.register(blockItem);
                });
    }
}
