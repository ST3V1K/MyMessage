package me.kryz.mymessage.common.tags.smallcaps;

import me.kryz.mymessage.common.tags.BaseTag;
import me.kryz.mymessage.common.tags.TagImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public final class SmallCapsTag implements BaseTag {

    private static final Map<Character, Character> SMALL_CAPS_MAP = new HashMap<>();
    public static final SmallCapsTag SMALL_CAPS_TAG = new SmallCapsTag();
    private final TextReplacementConfig config = this.replacementConfig();

    static {
        final String lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String smallCaps = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘꞯʀsᴛᴜᴠᴡxʏᴢ";

        for (int i = 0; i < lowercase.length(); i++) {
            SMALL_CAPS_MAP.put(lowercase.charAt(i), smallCaps.charAt(i));
            SMALL_CAPS_MAP.put(Character.toUpperCase(lowercase.charAt(i)), smallCaps.charAt(i));
        }
    }

    @Contract(pure = true)
    @Override
    public @NotNull Component process(final @NotNull Component component){
        return component.replaceText(config);
    }

    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable Set<String> getNames() {
        return Set.of("smallcaps", "sc");
    }

    @Override
    public @NotNull BiFunction<ArgumentQueue, Context, Tag> getFunction(final TagImpl impl) {
        return ((argumentQueue, context) -> impl);
    }

    private @NotNull String transform(final @NotNull String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            result.append(SMALL_CAPS_MAP.getOrDefault(c, c));
        }
        return result.toString();
    }

    private @NotNull TextReplacementConfig replacementConfig() {
        final TextReplacementConfig.Builder config = TextReplacementConfig.builder();
        config.match("[A-Za-zÀ-ÿ]").replacement((matchResult, builder) -> {
            final String text = this.transform(matchResult.group());
            return Component.text().content(text);
        });
        return config.build();
    }
}
