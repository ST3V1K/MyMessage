package me.kryz.mymessage.common.tags.actionbar;

import me.kryz.mymessage.common.processor.ComponentProcessor;
import me.kryz.mymessage.common.tags.PlayerTags;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class ActionBarTag implements PlayerTags {

    public static final ActionBarTag ACTION_BAR_TAG = new ActionBarTag();

    private ActionBarTag(){}

    @Override
    public @NotNull Component process(@NotNull Component component) {
        return component;
    }

    @Override
    public @NotNull Set<String> getNames() {
        return Set.of("actionbar", "acbar", "bar");
    }

    @Override
    public @NotNull TagResolver getTagResolver(OfflinePlayer player) {
        return TagResolver.resolver(getNames(), ((argumentQueue, context) -> {
            final String text = argumentQueue.popOr("Put a message").value();
            final var component = ComponentProcessor.asMiniMessage(text, player);
            ((Player) player).sendActionBar(component);
            return Tag.preProcessParsed("");
        }));
    }
}
