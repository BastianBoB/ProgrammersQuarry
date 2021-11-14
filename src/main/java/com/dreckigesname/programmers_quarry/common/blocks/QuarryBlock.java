package com.dreckigesname.programmers_quarry.common.blocks;



import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.common.menus.QuarryMenu;
import com.dreckigesname.programmers_quarry.core.init.BlockEntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public class QuarryBlock extends BaseHorizontalBlock implements EntityBlock {

    public static final VoxelShape SHAPE = Shapes.join(Block.box(4, 6, 5, 12, 13, 13), Block.box(2, 0, 0, 14, 6, 16), BooleanOp.OR);

    public QuarryBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion());
        runCalculation(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPES.get(state.getValue(HORIZONTAL_FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new QuarryBlockEntity(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == BlockEntityTypeInit.QUARRY_BLOCK_ENTITY_TYPE.get() ? QuarryBlockEntity::tick : null;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide){
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if(blockEntity instanceof QuarryBlockEntity){

               MenuProvider menuProvider = new MenuProvider() {
                   @Override
                   public Component getDisplayName() {
                       return new TextComponent("");
                   }

                   @Nullable
                   @Override
                   public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
                       return new QuarryMenu(windowId, level, blockPos, inventory, player);
                   }
               };
               NetworkHooks.openGui((ServerPlayer) player, menuProvider, blockPos);
               return InteractionResult.SUCCESS;
            }
        }

        return super.use(blockState, level, blockPos, player, hand, hitResult);
    }
}
