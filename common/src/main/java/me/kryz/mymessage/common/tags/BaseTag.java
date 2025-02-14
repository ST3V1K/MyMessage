package me.kryz.mymessage.common.tags;

import me.kryz.mymessage.common.tags.roman.RomanTag;
import me.kryz.mymessage.common.tags.smallcaps.SmallCapsTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;

public interface BaseTag {

    @NotNull Component process(final @NotNull Component component);

    @NotNull Set<String> getNames();

    @NotNull BiFunction<ArgumentQueue, Context, Tag> getFunction(final TagImpl tag);

    static Set<BaseTag> all(){
        return Set.of(SmallCapsTag.SMALL_CAPS_TAG,
                RomanTag.ROMAN_TAG);
    }
}
