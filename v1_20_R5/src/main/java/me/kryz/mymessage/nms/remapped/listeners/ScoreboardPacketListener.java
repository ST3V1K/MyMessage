package me.kryz.mymessage.nms.remapped.listeners;

import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.nms.remapped.ComponentSerializer;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import org.bukkit.entity.Player;

public final class ScoreboardPacketListener implements PacketListener<ClientboundSetObjectivePacket> {
    @Override
    public void read(Player player, PacketEvent<ClientboundSetObjectivePacket> event) {

    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSetObjectivePacket> event) {
        final var packet = event.getPacket();
        final var component = packet.getDisplayName();
        if(!Prefix.startsWith(component.getString())) return;
        final var json = ComponentSerializer.asJson(component);
        final var process = ComponentSerializer.process(json, player);
        event.setPacket(packet);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSetObjectivePacket.class;
    }
}
