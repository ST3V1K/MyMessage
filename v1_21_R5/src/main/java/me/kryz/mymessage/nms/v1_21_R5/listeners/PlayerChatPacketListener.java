package me.kryz.mymessage.nms.v1_21_R5.listeners;

import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.nms.v1_21_R5.ComponentSerializer;
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
        final String content = packet.body().content();
        if(!Prefix.startsWith(content))
            return;
        final var parsed = ComponentSerializer.textProcessor(content, player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);

        final var newPacket = new ClientboundPlayerChatPacket(packet.globalIndex(),
                packet.sender(),
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
