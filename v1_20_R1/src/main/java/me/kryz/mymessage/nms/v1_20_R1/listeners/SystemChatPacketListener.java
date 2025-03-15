package me.kryz.mymessage.nms.v1_20_R1.listeners;

import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.packet.PacketEvent;
import me.kryz.mymessage.common.packet.PacketListener;
import me.kryz.mymessage.common.serializer.parser.MinecraftJsonParser;
import me.kryz.mymessage.nms.v1_20_R1.ComponentSerializer;
import me.kryz.mymessage.utils.ReflectionUtils;
import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.entity.Player;

public final class SystemChatPacketListener implements PacketListener<ClientboundSystemChatPacket> {

    @Override
    public void read(Player var1, PacketEvent<ClientboundSystemChatPacket> var2) {

    }

    @Override
    public void write(Player player, PacketEvent<ClientboundSystemChatPacket> packetEvent) {
        final var packet = packetEvent.getPacket();
        //
        // Content
        final var f = ReflectionUtils.getField(getPacketClass(), "adventure$content");
        final var o = packet.content();
        var s = ReflectionUtils.getFieldValue(f, packet);
        if (s == null) {
            s = o;
        }
        // Plain String
        String text;
        if(s instanceof String str) {
            text = MinecraftJsonParser.toPlain(str);
        } else {
            text = ComponentSerializer.PLAIN_TEXT_COMPONENT_SERIALIZER.serialize((Component) s);
        }
        if(!Prefix.startsWith(text))
            return;
        // Json String
        String json;
        if (s instanceof String j) {
            json = j;
        } else {
            final Component component = (Component) s;
            json = ComponentSerializer.asJson(component);
        }
        //
        final var parsed = ComponentSerializer.process(json, player);
        final var toLegacy = ComponentSerializer.asLegacy(parsed);
        final var newPacket = new ClientboundSystemChatPacket(toLegacy,false);
        packetEvent.setPacket(newPacket);
    }

    @Override
    public Class<?> getPacketClass() {
        return ClientboundSystemChatPacket.class;
    }
}
