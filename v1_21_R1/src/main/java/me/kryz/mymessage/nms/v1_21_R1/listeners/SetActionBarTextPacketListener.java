package me.kryz.mymessage.nms.v1_21_R1.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.util.Prefix;
import me.kryz.mymessage.nms.v1_21_R1.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import org.bukkit.entity.Player;

public final class SetActionBarTextPacketListener implements PacketListener<ClientboundSetActionBarTextPacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundSetActionBarTextPacket> packetEvent) {

    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSetActionBarTextPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final Component component = packet.text();
        if(!Prefix.startsWith(component.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(component.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);
        final var newPacket = new ClientboundSetActionBarTextPacket(toLegacy);
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSetActionBarTextPacket.class;
    }
}
