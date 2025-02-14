package me.kryz.mymessage.common.tags;

import me.kryz.mymessage.common.tags.roman.RomanTag;
import me.kryz.mymessage.common.tags.smallcaps.SmallCapsTag;
import me.kryz.mymessage.common.tags.title.TitleTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public interface BaseTag {

    static final Set<BaseTag> TAGS = Collections.synchronizedSet(new HashSet<>());

    @NotNull Component process(final @NotNull Component component);

    @NotNull Set<String> getNames();

    static void add(final BaseTag tag) {
        TAGS.add(tag);
    }

    static boolean contains(final BaseTag tag) {
        return TAGS.contains(tag);
    }

    static Set<BaseTag> all() {
        return Collections.unmodifiableSet(TAGS);
    }
    
    static void defaults() {
        registerDefaultTags();
    }

    private static void registerDefaultTags() {
        add(SmallCapsTag.SMALL_CAPS_TAG);
        add(RomanTag.ROMAN_TAG);
        add(TitleTag.TITLE_TAG);
    }
}
