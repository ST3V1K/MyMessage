package me.kryz.mymessage.nms.v1_20_R3.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.util.Prefix;
import me.kryz.mymessage.nms.v1_20_R3.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import org.bukkit.entity.Player;

public final class SetSubtitleTextPacketListener implements PacketListener<ClientboundSetSubtitleTextPacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundSetSubtitleTextPacket> packetEvent) {
    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSetSubtitleTextPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final Component component = packet.getText();
        if(!Prefix.startsWith(component.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(component.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);
        final var newPacket = new ClientboundSetSubtitleTextPacket(toLegacy);
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSetSubtitleTextPacket.class;
    }
}
