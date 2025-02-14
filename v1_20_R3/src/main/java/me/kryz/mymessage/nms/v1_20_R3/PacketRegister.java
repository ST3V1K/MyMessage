package me.kryz.mymessage.nms.v1_20_R3;

import me.kryz.mymessage.nms.v1_20_R3.listeners.*;
import org.bukkit.plugin.Plugin;

public final class PacketRegister implements me.kryz.mymessage.common.packet.PacketRegister {

    @Override
    public void register(Plugin plugin) {
        PacketDispatcher.registrar(new PlayerChatPacketListener(), plugin);
        PacketDispatcher.registrar(new SystemChatPacketListener(), plugin);
        PacketDispatcher.registrar(new SetTitleTextPacketListener(), plugin);
        PacketDispatcher.registrar(new SetSubtitleTextPacketListener(), plugin);
        PacketDispatcher.registrar(new SetActionBarTextPacketListener(), plugin);
    }
}
