package me.kryz.mymessage.nms.v1_21_R5;

import com.google.gson.JsonParseException;
import me.kryz.mymessage.common.processor.ComponentProcessor;
import net.minecraft.network.chat.Component;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public final class ComponentSerializer extends ComponentProcessor {

    public static Component asLegacy(net.kyori.adventure.text.Component adventureComponent) {
        final String json =GSON_COMPONENT_SERIALIZER.serialize(adventureComponent);

        try {
            return CraftChatMessage.fromJSON(json);
        } catch (JsonParseException ignored) {
            return null;
        }
    }
    public static Component asLegacy(String adventureFormat) {
        final net.kyori.adventure.text.Component component = asMiniMessage(adventureFormat);
        final String json =GSON_COMPONENT_SERIALIZER.serialize(component);

        try {
            return CraftChatMessage.fromJSON(json);
        } catch (JsonParseException ignored) {
            return null;
        }
    }
    public static String asJson(net.kyori.adventure.text.Component component){
        return GSON_COMPONENT_SERIALIZER.serialize(component);
    }
}
