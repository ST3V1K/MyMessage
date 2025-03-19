package me.kryz.mymessage.common.packet;

import org.bukkit.entity.Player;

public interface PacketListener<T> {
    void read(Player player, PacketEvent<T> event);

    void write(Player player, PacketEvent<T> event);

    Class<?> getPacketClass();
}
