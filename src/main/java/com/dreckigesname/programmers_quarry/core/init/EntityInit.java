package com.dreckigesname.programmers_quarry.core.init;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.common.entities.QuarryEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ProgrammersQuarry.MOD_ID);

    public static final RegistryObject<EntityType<QuarryEntity>> QUARRY = ENTITIES.register("quarry",
            () -> EntityType.Builder.<QuarryEntity>of(QuarryEntity::new, MobCategory.AMBIENT).sized(1f, 1f)
                    .build(new ResourceLocation(ProgrammersQuarry.MOD_ID, "quarry").toString()));

}
