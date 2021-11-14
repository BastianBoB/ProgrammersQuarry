package com.dreckigesname.programmers_quarry.common.block_entities;

import com.dreckigesname.programmers_quarry.common.entities.QuarryEntity;
import com.dreckigesname.programmers_quarry.core.init.BlockEntityTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuarryBlockEntity extends BlockEntity {

    public List<String> code = new ArrayList<>();
    public int currentLine;
    public int waitCounter;
    public double speed = 1f;
    public boolean running;

    public QuarryBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState blockState) {
        super(p_155228_, p_155229_, blockState);
    }

    public QuarryBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(BlockEntityTypeInit.QUARRY_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("codeSize", code.size());
        for (int i = 0; i < code.size(); i++) {
            tag.putString("codeLine" + i, code.get(i));
        }
        tag.putInt("currentLine", currentLine);
        tag.putInt("waitCounter", waitCounter);
        tag.putDouble("speed", speed);
        tag.putBoolean("running", running);
        return new ClientboundBlockEntityDataPacket(getBlockPos(), -1, tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        int codeSize = tag.getInt("codeSize");
        code = new ArrayList<>();
        for (int i = 0; i < codeSize; i++) {
            code.add(i, tag.getString("codeLine" + i));
        }
        currentLine = tag.getInt("currentLine");
        waitCounter = tag.getInt("waitCounter");
        running = tag.getBoolean("running");
        speed = tag.getDouble("speed");
    }

    public void tick() {
        if (code.isEmpty() || !running) return;

        try {
            if (waitCounter < 0) {
                if (currentLine >= code.size()) {
                    running = false;
                } else {

                    String codeLine = code.get(currentLine).replaceAll("\s*", "");
                    for (int i = 0; i < codeLine.length(); i++) {
                        char c = codeLine.charAt(i);
                        if (c == ';' || c == '{' || c == '}') {
                            codeLine = codeLine.substring(0, i);
                            break;
                        }
                    }
                    currentLine++;

                    if (codeLine.equals("move")) {
                        spawnQuarryEntity(QuarryInstruction.MOVE, speed / 16f);
                    }
                    if (codeLine.equals("turnLeft")) {
                        spawnQuarryEntity(QuarryInstruction.TURN_LEFT, speed * 3.6f);
                    }
                    if (codeLine.equals("turnRight")) {
                        spawnQuarryEntity(QuarryInstruction.TURN_RIGHT, speed * 3.6f);
                    }
                }

            } else {
                waitCounter--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void spawnQuarryEntity(QuarryInstruction instruction, double speed) {
        BlockPos pos = this.getBlockPos();
        QuarryEntity quarry = new QuarryEntity(this.level, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f,
                this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING), instruction, code, currentLine, waitCounter, speed);

        this.level.addFreshEntity(quarry);
        this.level.removeBlock(pos, false);
    }


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (!level.isClientSide && blockEntity instanceof QuarryBlockEntity quarryBE) {
            quarryBE.tick();
        }
    }
}
