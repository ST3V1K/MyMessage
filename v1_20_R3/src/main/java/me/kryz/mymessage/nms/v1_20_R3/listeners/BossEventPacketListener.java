package me.kryz.mymessage.nms.v1_20_R3.listeners;

import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.nms.v1_20_R3.ComponentSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.server.bossevents.BossBattleCustom;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_20_R3.boss.CraftKeyedBossbar;
import org.bukkit.entity.Player;

public final class BossEventPacketListener implements PacketListener<PacketPlayOutBoss> {
    @Override
    public void read(Player var1, PacketEvent<PacketPlayOutBoss> var2) {

    }

    @Override
    public void write(Player var1, PacketEvent<PacketPlayOutBoss> var2) {
        ((CraftServer) Bukkit.getServer()).getServer().aL().b().forEach(customBossEvent -> {
            final var legacy = customBossEvent.a;
            if (!Prefix.startsWith(legacy.getString())) return;
            final var process = ComponentSerializer.process(ComponentSerializer.asJson(legacy), var1);
            customBossEvent.a(ComponentSerializer.asLegacy(process));
            var2.setPacket(PacketPlayOutBoss.c(customBossEvent));
        });
    }

    @Override
    public Class<?> getPacketClass() {
        return PacketPlayOutBoss.class;
    }
}
