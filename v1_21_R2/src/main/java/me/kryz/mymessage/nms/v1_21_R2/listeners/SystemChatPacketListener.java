package me.kryz.mymessage.nms.v1_21_R2.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.nms.v1_21_R2.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.entity.Player;

public final class SystemChatPacketListener implements PacketListener<ClientboundSystemChatPacket> {

    @Override
    public void read(Player var1, PacketEvent<ClientboundSystemChatPacket> var2) {

    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSystemChatPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final Component component = packet.content();
        if(!Prefix.startsWith(component.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(component.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);
        final var newPacket = new ClientboundSystemChatPacket(toLegacy,false);
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSystemChatPacket.class;
    }
}
