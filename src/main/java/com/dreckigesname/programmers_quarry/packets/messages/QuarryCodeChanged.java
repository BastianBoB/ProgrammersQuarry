package com.dreckigesname.programmers_quarry.packets.messages;

import com.dreckigesname.programmers_quarry.common.block_entities.QuarryBlockEntity;
import com.dreckigesname.programmers_quarry.common.menus.QuarryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class QuarryCodeChanged {

    public List<String> code;

    public QuarryCodeChanged(List<String> code){
        this.code = code;
    }

    public static void encode(QuarryCodeChanged message, FriendlyByteBuf buffer){
        buffer.writeInt(message.code.size());
        for(String str : message.code){
            buffer.writeUtf(str);
        }
    }

    public static QuarryCodeChanged decode(FriendlyByteBuf buffer){
        int size = buffer.readInt();
        List<String> code = new ArrayList<>();
        for(int i = 0; i < size; i++){
            code.add(buffer.readUtf());
        }
        return new QuarryCodeChanged(code);
    }

    public static void handle(QuarryCodeChanged message, Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
           Player player = context.getSender();
           AbstractContainerMenu menu = player.containerMenu;
           if(menu instanceof QuarryMenu quarryMenu){
               QuarryBlockEntity quarryBlockEntity = quarryMenu.getBlockEntity();
               quarryBlockEntity.code = message.code;
           }
        });
    }
}
