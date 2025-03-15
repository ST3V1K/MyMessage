package me.kryz.mymessage.nms.v1_20_R1;

import io.netty.channel.Channel;
import me.kryz.mymessage.utils.ReflectionUtils;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public final class Injector {

    public static void inject(Player player, Logger logger) {
        final PlayerConnection connection = ((CraftPlayer) player).getHandle().c;
        final Field field = ReflectionUtils.getField(PlayerConnection.class, "h");
        final NetworkManager networkManager = (NetworkManager) ReflectionUtils.getFieldValue(field, connection);
        final Channel channel = networkManager.m;;
        final var pipeline = channel.pipeline();
        if (pipeline.get("mymessage_listener") == null) {
            pipeline.addBefore("packet_handler", "mymessage_listener",
                    new PacketDispatcher(logger, player.getUniqueId(), player.getName()));
        }
    }
}
