package me.kryz.mymessage.common.tags;

import me.kryz.mymessage.common.tags.papi.PapiTag;
import me.kryz.mymessage.common.tags.roman.RomanTag;
import me.kryz.mymessage.common.tags.smallcaps.SmallCapsTag;
import me.kryz.mymessage.common.tags.title.TitleTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public interface BaseTag {

    Set<NormalTags> NORMAL_TAGS = new HashSet<>();
    Set<PlayerTags> PLAYER_TAGS = new HashSet<>();

    @NotNull Component process(final @NotNull Component component);

    @NotNull Set<String> getNames();

    static void addNormal(final NormalTags tag) {
        NORMAL_TAGS.add(tag);
    }
    static void addPlayer(final PlayerTags tag) {
        PLAYER_TAGS.add(tag);
    }

    static Set<NormalTags> allNormal() {
        return NORMAL_TAGS;
    }

    static Set<PlayerTags> allPlayers() {
        return PLAYER_TAGS;
    }
    
    static void defaults() {
        registerDefaultTags();
    }

    private static void registerDefaultTags() {
        addNormal(SmallCapsTag.SMALL_CAPS_TAG);
        addNormal(RomanTag.ROMAN_TAG);
        addPlayer(TitleTag.TITLE_TAG);
        addPlayer(PapiTag.PAPI_TAG);
    }
}
