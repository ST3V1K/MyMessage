package me.kryz.mymessage.nms.v1_21_R3;

import io.netty.channel.Channel;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
public final class Injector {

    public static void inject(Player player, Logger logger) {
        final ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        final Connection networkManager = connection.connection;
        final Channel channel = networkManager.channel;;
        final var pipeline = channel.pipeline();
        if (pipeline.get("mymessage_listener") == null) {
            pipeline.addBefore("packet_handler", "mymessage_listener",
                    new PacketDispatcher(logger, player.getUniqueId(), player.getName()));
            System.out.println(pipeline.get("mymessage_listener"));
        }
    }
}
