package me.kryz.mymessage.nms.v1_21_R3;

import org.bukkit.entity.Player;
import org.slf4j.Logger;

public final class PacketHandler implements me.kryz.mymessage.common.packet.PacketHandler {

    private final Logger logger;

    public PacketHandler(Logger logger) {
        this.logger = logger;
    }
    @Override
    public void interceptPackets(Player player) {
        Injector.inject(player, logger);
    }
}
