package me.kryz.mymessage.nms.v1_20_R5;

import me.kryz.mymessage.common.processor.ComponentProcessor;
import net.minecraft.network.chat.Component;
import org.bukkit.craftbukkit.CraftRegistry;

public final class ComponentSerializer extends ComponentProcessor {

    public static Component asLegacy(net.kyori.adventure.text.Component adventureComponent) {
        final String json = GSON_COMPONENT_SERIALIZER.serialize(adventureComponent);

        return Component.Serializer.fromJson(json, CraftRegistry.getMinecraftRegistry());
    }
    public static Component asLegacy(String adventureFormat) {
        final net.kyori.adventure.text.Component component = asMiniMessage(adventureFormat);
        final String json = GSON_COMPONENT_SERIALIZER.serialize(component);

        return Component.Serializer.fromJson(json, CraftRegistry.getMinecraftRegistry());
    }
    public static String asJson(net.kyori.adventure.text.Component component){
        return GSON_COMPONENT_SERIALIZER.serialize(component);
    }
}
