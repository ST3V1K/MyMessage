package me.kryz.mymessage.common.serializer.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MinecraftJsonParser {

    public static String toPlain(final String json) {
        final JsonObject component = JsonParser.parseString(json).getAsJsonObject();
        final List<String> extraComponents = new ArrayList<>();

        final String text = getString(component, "text", "");
        applyExtraComponents(extraComponents, component, true);
        MinecraftComponent baseComponent = new TextComponent(text, null, null, null, null, null, null, extraComponents);

        baseComponent = applyTranslation(baseComponent, component, true, extraComponents);

        return baseComponent.toPlain();
    }

    public static String toMini(final JsonObject component) {
        final List<String> extraComponents = new ArrayList<>();

        final String text = getString(component, "text", "");
        final String color = getString(component, "color", null);
        final Boolean bold = getBoolean(component, "bold");
        final Boolean italic = getBoolean(component, "italic");
        final Boolean underlined = getBoolean(component, "underlined");
        final Boolean strikethrough = getBoolean(component, "strikethrough");
        final Boolean obfuscated = getBoolean(component, "obfuscated");

        applyExtraComponents(extraComponents, component, false);
        MinecraftComponent baseComponent = new TextComponent(text, color, bold, italic, underlined, strikethrough, obfuscated, extraComponents);

        baseComponent = applyClickEvent(baseComponent, component);
        baseComponent = applyHoverEvent(baseComponent, component);
        baseComponent = applyTranslation(baseComponent, component, false, extraComponents);

        return baseComponent.toMini();
    }

    private static MinecraftComponent applyClickEvent(final MinecraftComponent base, final JsonObject component) {
        if (component.has("clickEvent")) {
            final JsonObject clickEvent = component.getAsJsonObject("clickEvent");
            return new ClickEventComponent(
                    base,
                    getString(clickEvent, "action", ""),
                    getString(clickEvent, "value", "")
            );
        }
        return base;
    }

    private static MinecraftComponent applyHoverEvent(final MinecraftComponent base, final JsonObject component) {
        if (component.has("hoverEvent")) {
            final JsonObject hoverEvent = component.getAsJsonObject("hoverEvent");
            return new HoverEventComponent(
                    base,
                    getString(hoverEvent, "action", ""),
                    toMini(hoverEvent.getAsJsonObject("contents"))
            );
        }
        return base;
    }

    private static MinecraftComponent applyTranslation(final MinecraftComponent base, final JsonObject component, final boolean plain, final List<String> extra) {
        if (component.has("translate")) {

            final String translate = component.get("translate").getAsString();
            final List<String> list = new ArrayList<>();

            if (component.has("with") && component.get("with").isJsonArray()) {
                for (final JsonElement jsonElement : component.getAsJsonArray("with")) {
                    if (jsonElement.isJsonObject()) {
                        list.add(plain ? toPlain(jsonElement.getAsJsonObject().toString()) : toMini(jsonElement.getAsJsonObject()));
                    } else if (jsonElement.isJsonPrimitive()) {
                        list.add(new TextComponent(jsonElement.getAsString(), null, false, false, false, false, false, null).toPlain());
                    }
                }
            }
//
            final TranslatableComponent translatable = new TranslatableComponent(translate, list);

            if(!extra.isEmpty()){
                return new TextComponent(
                        "", null, null, null,
                        null, null, null,
                        Arrays.asList(
                                plain ? translatable.toPlain() : translatable.toMini(),
                                new TextComponent("", null, null, null,
                                        null, null, null,
                                        extra).toPlain()
                        )
                );
            }
        }

        return base;
    }

    private static void applyExtraComponents(final List<String> extraComponents, final JsonObject component, final boolean plain) {
        if (component.has("extra") && component.get("extra").isJsonArray()) {
            for (final JsonElement extraElement : component.getAsJsonArray("extra")) {
                if (extraElement.isJsonObject()) {
                    final JsonObject extraObj = extraElement.getAsJsonObject();

                    extraComponents.add(plain ? toPlain(extraObj.toString()) : toMini(extraObj));
                } else if (extraElement.isJsonPrimitive()) {
                    extraComponents.add(new TextComponent(extraElement.getAsString(), null, null, null, null, null, null, null).toPlain());
                }
            }
        }
    }

    private static String getString(final JsonObject obj, final String key, final String defaultValue) {
        return obj.has(key) ? obj.getAsJsonPrimitive(key).getAsString() : defaultValue;
    }

    private static Boolean getBoolean(final JsonObject obj, final String key) {
        return obj.has(key) ? obj.getAsJsonPrimitive(key).getAsBoolean() : null;
    }
}

