package me.kryz.mymessage.common.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class AdventureSerializer {

    public static JsonObject toJson(final Component component) {
        final JsonObject json = new JsonObject();

        if (component instanceof TextComponent textComponent) {
            json.addProperty("text", textComponent.content());
        }
        if (component instanceof TranslatableComponent translatable) {
            json.addProperty("translate", translatable.key());

            final JsonArray withArray = new JsonArray();
            for (TranslationArgument arg : translatable.arguments()) {
                withArray.add(toJson(arg.asComponent()));
            }
            json.add("with", withArray);
        }
        if(component instanceof ScoreComponent scoreComponent){
            json.add("score", scoreComponent(scoreComponent));
        }
        if(component instanceof SelectorComponent selectorComponent){
            json.addProperty("selector", selectorComponent.pattern());
        }
        if(component instanceof NBTComponent<?,?> nbtComponent) {

            if (component instanceof BlockNBTComponent blockNBTComponent) {
                json.addProperty("block", blockNBTComponent.pos().asString());
            }
            if (component instanceof EntityNBTComponent entityNBTComponent) {
                json.addProperty("entity", entityNBTComponent.selector());
            }
            if (component instanceof StorageNBTComponent entityNBTComponent) {
                json.addProperty("storage", entityNBTComponent.storage().value());
            }

            json.addProperty("nbt", nbtComponent.nbtPath());
            json.addProperty("interpret", nbtComponent.interpret());
            json.add("separator", toJson(nbtComponent.separator()));
        }
        if(component instanceof KeybindComponent keybindComponent) {
            json.addProperty("keybind", keybindComponent.keybind());
        }

        serializeStyle(json, component.style());

        if (!component.children().isEmpty()) {
            final JsonArray extra = new JsonArray();
            for (Component child : component.children()) {
                extra.add(toJson(child));
            }
            json.add("extra", extra);
        }

        return json;
    }

    private static JsonObject scoreComponent(final ScoreComponent scoreComponent){
        final var commas = "\"";
        String jsonObject1 = "{" +
                "\"name\":" +
                commas + scoreComponent.name() + commas + "," +
                "\"objective\":" +
                commas + scoreComponent.objective() + commas +
                "}";

        return JsonParser.parseString(jsonObject1).getAsJsonObject();
    }

    private static void serializeStyle(final JsonObject json, final Style style) {
        final TextColor color = style.color();
        final String insert = style.insertion();
        final var font = style.font();
        if (color != null) {
            json.addProperty("color", color.toString());
        }
        if (style.decoration(TextDecoration.BOLD) != TextDecoration.State.NOT_SET) {
            json.addProperty("bold", style.decoration(TextDecoration.BOLD).toString());
        }
        if (style.decoration(TextDecoration.ITALIC) != TextDecoration.State.NOT_SET) {
            json.addProperty("italic", style.decoration(TextDecoration.ITALIC).toString());
        }
        if (style.decoration(TextDecoration.UNDERLINED) != TextDecoration.State.NOT_SET) {
            json.addProperty("underlined", style.decoration(TextDecoration.UNDERLINED).toString());
        }
        if (style.decoration(TextDecoration.STRIKETHROUGH) != TextDecoration.State.NOT_SET) {
            json.addProperty("strikethrough", style.decoration(TextDecoration.STRIKETHROUGH).toString());
        }
        if (style.decoration(TextDecoration.OBFUSCATED) != TextDecoration.State.NOT_SET) {
            json.addProperty("obfuscated", style.decoration(TextDecoration.OBFUSCATED).toString());
        }
        if (insert != null) {
            if(insert.isEmpty()) return;
            json.addProperty("insertion", insert);
        }
        if (font != null) {
            json.addProperty("font", font.asString());
        }

        final ClickEvent clickEvent = style.clickEvent();
        if (clickEvent != null) {
            final JsonObject clickJson = new JsonObject();
            clickJson.addProperty("action", clickEvent.action().toString().toLowerCase());
            clickJson.addProperty("value", clickEvent.value());
            json.add("clickEvent", clickJson);
        }

        final HoverEvent<?> hoverEvent = style.hoverEvent();
        if (hoverEvent != null) {
            final JsonObject hoverJson = new JsonObject();
            hoverJson.addProperty("action", hoverEvent.action().toString().toLowerCase());
            hoverJson.add("contents", toJson((Component) hoverEvent.value()));
            json.add("hoverEvent", hoverJson);
        }
    }
}
