package com.dreckigesname.programmers_quarry.common.menus;

import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.core.init.BlockInit;
import com.dreckigesname.programmers_quarry.core.init.MenuTypeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class QuarryMenu extends AbstractContainerMenu {

    private final QuarryBlockEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;

    public static final int gz = 18;

    public QuarryMenu(int windowId, Level world, BlockPos pos, Inventory inventory, Player player) {
        super(MenuTypeInit.QUARRY_MENU.get(), windowId);
        this.blockEntity = (QuarryBlockEntity) world.getBlockEntity(pos);
        this.containerLevelAccess = ContainerLevelAccess.create(world, pos);

        //Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = col + row * 9 + 9;
                this.addSlot(new Slot(inventory, slot, 46 + col * gz, 119 + row * gz));
            }
        }

        //Player Hotbar
        for(int row = 0; row < 9; row++) {
            this.addSlot(new Slot(inventory, row, 46 + row * gz, 178));
        }
    }

    public QuarryBlockEntity getBlockEntity(){
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(containerLevelAccess, player, BlockInit.QUARRY.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        int numSlots = 9;

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < numSlots) {
                if (!this.moveItemStackTo(itemstack1, numSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, numSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}
