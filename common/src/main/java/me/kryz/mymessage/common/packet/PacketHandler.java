package me.kryz.mymessage.common.packet;

import org.bukkit.entity.Player;

public interface PacketHandler {
    void interceptPackets(final Player player);
}
