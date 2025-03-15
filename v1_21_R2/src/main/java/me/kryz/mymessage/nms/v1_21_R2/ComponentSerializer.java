package me.kryz.mymessage.nms.v1_21_R2;

import me.kryz.mymessage.common.processor.ComponentProcessor;
import net.minecraft.network.chat.Component;
import org.bukkit.craftbukkit.CraftRegistry;

public final class ComponentSerializer extends ComponentProcessor {

    public static Component asLegacy(net.kyori.adventure.text.Component adventureComponent) {
        final String json =GSON_COMPONENT_SERIALIZER.serialize(adventureComponent);

        return Component.Serializer.fromJson(json, CraftRegistry.getMinecraftRegistry());
    }
    public static Component asLegacy(String adventureFormat) {
        final net.kyori.adventure.text.Component component = asMiniMessage(adventureFormat);
        final String json =GSON_COMPONENT_SERIALIZER.serialize(component);

        return Component.Serializer.fromJson(json, CraftRegistry.getMinecraftRegistry());
    }
//    public static Component buildNMSComponent(final Component component, final Player player){
//        final String json = Component.Serializer.toJson(component, CraftRegistry.getMinecraftRegistry());
//        final var mini = MinecraftJsonParser.toMini(JsonParser.parseString(json).getAsJsonObject());
//        System.out.println(mini);
//        final var to = textProcessor(mini, player);
//        return asLegacy(to);
////        return asLegacy(to);
//    }
    public static String asJson(net.kyori.adventure.text.Component component){
        return GSON_COMPONENT_SERIALIZER.serialize(component);
    }
}
