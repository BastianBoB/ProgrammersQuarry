package com.dreckigesname.programmers_quarry.core.init;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.blocks.QuarryBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ProgrammersQuarry.MOD_ID);

    public static final RegistryObject<Block> QUARRY = BLOCKS.register("quarry", QuarryBlock::new);
}
