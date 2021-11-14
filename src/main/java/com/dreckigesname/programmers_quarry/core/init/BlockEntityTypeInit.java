package com.dreckigesname.programmers_quarry.core.init;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockEntityTypeInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ProgrammersQuarry.MOD_ID);

    public static final RegistryObject<BlockEntityType<QuarryBlockEntity>> QUARRY_BLOCK_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("quarry_block_entity",
            () -> BlockEntityType.Builder.of(QuarryBlockEntity::new, BlockInit.QUARRY.get()).build(null));
}
