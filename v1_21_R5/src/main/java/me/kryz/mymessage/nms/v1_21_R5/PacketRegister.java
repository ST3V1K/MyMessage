package me.kryz.mymessage.nms.v1_21_R5;

import me.kryz.mymessage.nms.v1_21_R5.listeners.*;
import me.kryz.mymessage.nms.v1_21_R5.listeners.PlayerChatPacketListener;
import me.kryz.mymessage.nms.v1_21_R5.listeners.SetActionBarTextPacketListener;
import me.kryz.mymessage.nms.v1_21_R5.listeners.SetSubtitleTextPacketListener;
import me.kryz.mymessage.nms.v1_21_R5.listeners.SetTitleTextPacketListener;
import me.kryz.mymessage.nms.v1_21_R5.listeners.SystemChatPacketListener;
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
