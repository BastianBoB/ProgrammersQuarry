package com.dreckigesname.programmers_quarry.packets.messages;


import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.common.menus.QuarryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class QuarrySetRunning {

    public boolean running;

    public QuarrySetRunning(boolean running) {
        this.running = running;
    }

    public static void encode(QuarrySetRunning message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.running);
    }

    public static QuarrySetRunning decode(FriendlyByteBuf buffer) {
        return new QuarrySetRunning(buffer.readBoolean());
    }

    public static void handle(QuarrySetRunning message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            AbstractContainerMenu menu = player.containerMenu;
            if (menu instanceof QuarryMenu quarryMenu) {
                quarryMenu.getBlockEntity().running = message.running;
                quarryMenu.getBlockEntity().currentLine = 0;
            }
        });
    }
}
