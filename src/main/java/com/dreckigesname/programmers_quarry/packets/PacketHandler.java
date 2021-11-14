package com.dreckigesname.programmers_quarry.packets;

import com.dreckigesname.programmers_quarry.ProgrammersQuarry;
import com.dreckigesname.programmers_quarry.packets.messages.QuarryCodeChanged;
import com.dreckigesname.programmers_quarry.packets.messages.QuarrySetRunning;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class PacketHandler {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ProgrammersQuarry.MOD_ID, "network"),
            () -> NETWORK_VERSION, version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION));

    public static void init(){
        CHANNEL.registerMessage(0, QuarryCodeChanged.class, QuarryCodeChanged::encode, QuarryCodeChanged::decode, QuarryCodeChanged::handle);
        CHANNEL.registerMessage(1, QuarrySetRunning.class, QuarrySetRunning::encode, QuarrySetRunning::decode, QuarrySetRunning::handle);
    }
}
