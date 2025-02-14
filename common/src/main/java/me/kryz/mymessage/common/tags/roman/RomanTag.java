package me.kryz.mymessage.common.tags.roman;

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

import java.util.Set;
import java.util.function.BiFunction;

public final class RomanTag implements BaseTag {

    private static final int[] ARABIC = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static final RomanTag ROMAN_TAG = new RomanTag();

    private final TextReplacementConfig config = this.replacementConfig();

    public @NotNull String toRoman(final String text){
        int integer = (int) Math.round(Double.parseDouble(text));
        System.out.println(integer);
        if (integer <= 0 || integer > 3999) {
            throw new IllegalArgumentException("The number must be between 1 and 3999");
        }
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < ARABIC.length; i++) {
            while (integer >= ARABIC[i]) {
                result.append(ROMAN[i]);
                integer -= ARABIC[i];
            }
        }
        return result.toString();
    }

    @Contract(pure = true)
    public @NotNull Component process(final @NotNull Component component){
        return component.replaceText(config);
    }

    public @NotNull @Unmodifiable Set<String> getNames() {
        return Set.of("roman", "latin", "rom");
    }

    public @NotNull BiFunction<ArgumentQueue, Context, Tag> getFunction(TagImpl tag) {
        return ((argumentQueue, context) -> tag);
    }

    public TextReplacementConfig replacementConfig(){
        final TextReplacementConfig.Builder textBuilder = TextReplacementConfig.builder();
        textBuilder.match("\\d+(\\.\\d+)?").replacement((matchResult, builder) -> {
            final var text = this.toRoman(matchResult.group());
            return Component.text().content(text);
        });
        return textBuilder.build();
    }
}
