package me.kryz.mymessage.common.tags.title;

import me.kryz.mymessage.common.audience.MyAdventureUtils;
import me.kryz.mymessage.common.processor.ComponentProcessor;
import me.kryz.mymessage.common.tags.PlayerTags;
import me.kryz.mymessage.common.util.NumberParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Set;

public final class TitleTag implements PlayerTags{

    public static final TitleTag TITLE_TAG = new TitleTag();

    private TitleTag() {
    }

    private TagResolver createTitleResolver(final OfflinePlayer player) {
        return TagResolver.resolver(getNames(), (argumentQueue, context) -> {
            final String arguments = argumentQueue.popOr("The title tag must has the arguments.").value();
            final String[] args = arguments.split(":");
            final String title = args[0];
            final String subtitle = args[1].isEmpty() ? "" : args[1];
            final int fadeIn = NumberParser.parsePositiveInt(args[2], 20);
            final int stay = NumberParser.parsePositiveInt(args[3], 20);
            final int fadeOut = NumberParser.parsePositiveInt(args[4], 20);
            final var titleComponent = Title.title(ComponentProcessor.asMiniMessage(title),
                    ComponentProcessor.asMiniMessage(subtitle),
                    Title.Times.times(
                            Duration.ofMillis(fadeIn * 50L), // 20 * 50 = 1000ms = 1s
                            Duration.ofMillis(stay * 50L),
                            Duration.ofMillis(fadeOut * 50L)
                    ));
            MyAdventureUtils.player((Player) player).showTitle(titleComponent);
            return Tag.preProcessParsed("");
        });
    }

    @Override
    public @NotNull Component process(@NotNull Component component) {
        return Component.empty();
    }

    @Override
    public @NotNull Set<String> getNames() {
        return Set.of("title");
    }

    @Override
    public @NotNull TagResolver getTagResolver(OfflinePlayer player) {
        return createTitleResolver(player);
    }
}
