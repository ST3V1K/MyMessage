package me.kryz.mymessage.common.packet;

import org.bukkit.entity.Player;

public interface PacketListener<T> {
    void read(Player var1, PacketEvent<T> var2);

    void write(Player var1, PacketEvent<T> var2);

    Class<?> getPacketClass();
}
