package me.kryz.mymessage.nms.remapped.listeners;

import io.papermc.paper.adventure.PaperAdventure;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.nms.remapped.ComponentSerializer;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.server.bossevents.CustomBossEvent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

public final class BossEventPacketListener implements PacketListener<ClientboundBossEventPacket> {
    @Override
    public void read(Player var1, PacketEvent<ClientboundBossEventPacket> var2) {
    }

    @Override
    public void write(Player var1, PacketEvent<ClientboundBossEventPacket> var2) {
        ((CraftServer) Bukkit.getServer()).getServer().getCustomBossEvents().getEvents().forEach(customBossEvent -> {
            final var legacy = customBossEvent.getName();
            if (!Prefix.startsWith(legacy.getString())) return;
            final var process = ComponentSerializer.process(ComponentSerializer.asJson(legacy), var1);
            customBossEvent.setName(PaperAdventure.asVanilla(process));
            var2.setPacket(ClientboundBossEventPacket.createUpdateNamePacket(customBossEvent));
        });
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundBossEventPacket.class;
    }
}
