package me.kryz.mymessage.nms.v1_21_R5.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.nms.v1_21_R5.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import org.bukkit.entity.Player;

public final class SetTitleTextPacketListener implements PacketListener<ClientboundSetTitleTextPacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundSetTitleTextPacket> packetEvent) {
    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSetTitleTextPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final Component component = packet.text();
        if(!Prefix.startsWith(component.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(component.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);
        final var newPacket = new ClientboundSetTitleTextPacket(toLegacy);
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSetTitleTextPacket.class;
    }
}
