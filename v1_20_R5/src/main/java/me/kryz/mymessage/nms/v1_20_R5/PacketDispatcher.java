package me.kryz.mymessage.nms.v1_20_R5;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import it.unimi.dsi.fastutil.Pair;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;

import java.util.UUID;

public final class PacketDispatcher extends ChannelDuplexHandler {

    private final static Multimap<Class<?>, PacketListener<?>> LISTENERS = MultimapBuilder
            .hashKeys()
            .arrayListValues()
            .build();

    private final static Multimap<Plugin, Pair<Class<?>, PacketListener<?>>> LISTENERS_BY_PLUGIN = MultimapBuilder
            .hashKeys()
            .hashSetValues()
            .build();

    private final Logger logger;
    private final String playerName;
    private UUID playerUUID;

    public PacketDispatcher(final Logger logger, final UUID uuid, final String name) {
        this.logger = logger;
        this.playerName = name;
        this.playerUUID = uuid;;
    }

    public static <T> void registrar(final PacketListener<T> packetListener, final Plugin plugin) {
        LISTENERS.put(packetListener.getPacketClass(), packetListener);
        LISTENERS_BY_PLUGIN.put(plugin, Pair.of(packetListener.getPacketClass(), packetListener));
    }

    public static void removeAll(Plugin plugin) {
        final var listeners = LISTENERS_BY_PLUGIN.removeAll(plugin);
        for (final var listener : listeners) {
            LISTENERS.remove(listener.left(), listener.right());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object rawPacket) throws Exception {
        handleRead(ctx, rawPacket);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!isPackets(msg)){
            super.write(ctx, msg, promise);
            return;
        }
        handleWrite(ctx, msg, promise);
    }

    private <T> void handleWrite(final ChannelHandlerContext ctx, final T packet, final ChannelPromise promise) throws Exception {
        final var listeners = LISTENERS.get(packet.getClass());
        if (listeners.isEmpty()) {
            super.write(ctx, packet, promise);
            return;
        }
        if (playerUUID == null) {
            final var player = Bukkit.getPlayer(playerName);
            if (player != null) {
                playerUUID = player.getUniqueId();
                logger.info("[Packet-Interceptor] Found UUID for {} after packet listener initializer", playerName);
            } else {
                logger.warn("[Packet-Interceptor] No player found for {} in {}, is the player online?", playerName, packet.getClass().getSimpleName()); ;
                super.write(ctx, packet, promise);
                return;
            }
        }

        final var player = Bukkit.getPlayer(playerUUID);
        if (player == null || !player.isOnline()) {
            super.write(ctx, packet, promise);
            return;
        }

        final var event = new PacketEvent<>(packet);
        try {
            for (Object rawListener : listeners) {
                final var listener = (PacketListener<T>) rawListener;
                listener.write(player, event);

                if (event.isCancelled()) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("[Packet-Interceptor] Error while writing packet", e);
        } finally {
            if (!event.isCancelled()) {
                super.write(ctx, event.getPacket(), promise);
            }
        }
    }

    private <T> void handleRead(final ChannelHandlerContext ctx, final T packet) throws Exception {
        final var listeners = LISTENERS.get(packet.getClass());
        if (listeners.isEmpty()) {
            super.channelRead(ctx, packet);
            return;
        }

        if (playerUUID == null) {
            final var player = Bukkit.getPlayer(playerName);
            if (player != null) {
                playerUUID = player.getUniqueId();
                logger.info("[Packet-Interceptor] Found UUID for {} after packet listener initializer", playerName);
            } else {
                logger.warn("[Packet-Interceptor] No player found for {} in {}, is the player online?", playerName, packet.getClass().getSimpleName());

                super.channelRead(ctx, packet);
                return;
            }
        }

        final var player = Bukkit.getPlayer(playerUUID);
        if (player == null || !player.isOnline()) {
            super.channelRead(ctx, packet);
            return;
        }

        final var event = new PacketEvent<>(packet);
        try {
            for (Object rawListener : listeners) {
                final var listener = (PacketListener<T>) rawListener;
                listener.read(player, event);

                if (event.isCancelled()) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("[Packet-Interceptor] Error while reading packet", e);
        } finally {
            if (!event.isCancelled()) {
                super.channelRead(ctx, event.getPacket());
            }
        }
    }

    private boolean isPackets(final Object msg){
        return msg instanceof ClientboundPlayerChatPacket ||
                msg instanceof ClientboundSystemChatPacket;
    }
}
