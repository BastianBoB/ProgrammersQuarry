package com.dreckigesname.programmers_quarry.common.entities;

import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.common.block_entities.QuarryInstruction;
import com.dreckigesname.programmers_quarry.core.init.BlockInit;
import com.dreckigesname.programmers_quarry.core.init.EntityInit;
import com.dreckigesname.programmers_quarry.core.init.ItemInit;
import com.mojang.math.Vector3d;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import org.lwjgl.system.CallbackI;

import java.util.List;

public class QuarryEntity extends Entity {

    public List<String> code;
    public int currentLine;
    public int waitCounter;
    public double speed;
    public int tickCount;

    public Direction direction;
    public QuarryInstruction instruction;

    public BlockPos startPos;

    public QuarryEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public QuarryEntity(Level world, double x, double y, double z, Direction direction,
                        QuarryInstruction instruction, List<String> code, int currentLine, int waitCounter, double speed) {
        super(EntityInit.QUARRY.get(), world);
        this.setPos(x, y, z);
        this.setRotation(direction);
        this.direction = direction;
        this.code = code;
        this.currentLine = currentLine;
        this.waitCounter = waitCounter;
        this.instruction = instruction;
        this.speed = speed;

        this.startPos = new BlockPos(x, y, z);
    }

    private void setRotation(Direction direction) {
        this.setYRot(direction.toYRot());
    }

    private Vec3 getMoveVectorForDirection(Direction direction, double factor) {
        Vec3 moveVec = switch (direction) {
            case NORTH -> new Vec3(0, 0, -1);
            case SOUTH -> new Vec3(0, 0, 1);
            case WEST -> new Vec3(-1, 0, 0);
            case EAST -> new Vec3(1, 0, 0);
            case DOWN -> new Vec3(0, -1, 0);
            case UP -> new Vec3(0, 1, 0);
        };

        return moveVec.multiply(factor, factor, factor);
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }

    @Override
    public void tick() {
        super.tick();
        xRotO = getXRot();
        yRotO = getYRot();

        if(!this.level.isClientSide) {

            Vec3 mov = this.getDeltaMovement();
            double d2 = this.getX() + mov.x;
            double d0 = this.getY() + mov.y;
            double d1 = this.getZ() + mov.z;
            this.setPos(d2, d0, d1);

            if (instruction == QuarryInstruction.MOVE) {
                Vec3 moveVec = getMoveVectorForDirection(direction, speed);
                this.setDeltaMovement(moveVec);

                if (tickCount > 0.3 / speed) {
                    Vec3 vec = getMoveVectorForDirection(direction, 1);
                    this.level.destroyBlock(startPos.offset(vec.x, vec.y, vec.z), true);
                }
                if (tickCount > 1 / speed) {
                    setQuarryBlockEntity(this.direction);
                }
            }

            if (instruction == QuarryInstruction.TURN_LEFT) {
                this.setYRot((float) (this.getYRot() - speed));
                if (tickCount > 90 / speed) {
                    Direction newDirection = switch (direction) {
                        case NORTH -> Direction.WEST;
                        case WEST -> Direction.SOUTH;
                        case SOUTH -> Direction.EAST;
                        case EAST -> Direction.NORTH;
                        default -> Direction.NORTH;
                    };
                    setQuarryBlockEntity(newDirection);
                }
            }

            tickCount++;
        }
    }

    private void setQuarryBlockEntity(Direction direction) {
        BlockPos pos = this.blockPosition();
        BlockState state = BlockInit.QUARRY.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);

        this.level.setBlock(pos, state, Constants.BlockFlags.BLOCK_UPDATE);
        BlockEntity blockEntity = this.level.getBlockEntity(pos);
        if (blockEntity instanceof QuarryBlockEntity quarryBlockEntity) {
            quarryBlockEntity.code = code;
            quarryBlockEntity.currentLine = currentLine;
            quarryBlockEntity.waitCounter = waitCounter;
            quarryBlockEntity.running = true;
            quarryBlockEntity.setChanged();
            this.level.setBlocksDirty(this.blockPosition(), state, state);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
