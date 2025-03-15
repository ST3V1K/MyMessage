package me.kryz.mymessage.nms.remapped.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.nms.remapped.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.bukkit.entity.Player;

public final class PlayerChatPacketListener implements PacketListener<ClientboundPlayerChatPacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundPlayerChatPacket> packetEvent) {
    }

    @Override
    public void write(Player player, PacketEvent<ClientboundPlayerChatPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        final Component unsigned = packet.unsignedContent();
        if(unsigned == null)
            return;
        if(!Prefix.startsWith(unsigned.getString()))
            return;
        final var parsed = ComponentSerializer.textProcessor(unsigned.getString(), player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);

        final var newPacket = new ClientboundPlayerChatPacket(packet.sender(),
                packet.index(),
                packet.signature(),
                packet.body(),
                toLegacy,
                packet.filterMask(),
                packet.chatType());
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundPlayerChatPacket.class;
    }
}
