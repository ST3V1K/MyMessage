package me.kryz.mymessage.nms.v1_20_R2.listeners;

import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.nms.v1_20_R2.ComponentSerializer;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.bukkit.entity.Player;

public final class PlayerChatPacketListener implements PacketListener<ClientboundPlayerChatPacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundPlayerChatPacket> packetEvent) {
    }

    @Override
    public void write(Player player, PacketEvent<ClientboundPlayerChatPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final var component = packet.g();
        if(component == null)
            return;
        if(!Prefix.startsWith(component.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(component.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);

        final var newPacket = new ClientboundPlayerChatPacket(
                packet.a(),
                packet.d(),
                packet.e(),
                packet.f(),
                toLegacy,
                packet.h(),
                packet.i());
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundPlayerChatPacket.class;
    }
}
