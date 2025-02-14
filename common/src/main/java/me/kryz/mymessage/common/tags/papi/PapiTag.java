package me.kryz.mymessage.common.tags.papi;

import me.kryz.mymessage.common.papi.PAPIHook;
import me.kryz.mymessage.common.tags.PlayerTags;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class PapiTag implements PlayerTags{
    private TagResolver createPapiResolver(final OfflinePlayer player) {
        return TagResolver.resolver(Set.of("papi", "placeholderapi"), (argumentQueue, context) -> {
            final String placeholder = argumentQueue.popOr("The papi tag must be a placeholder.").value();
            final String parsed = PAPIHook.formatString('%' + placeholder + '%', player);
            return Tag.preProcessParsed(parsed);
        });
    }

    @Override
    public @NotNull Component process(@NotNull Component component) {
        return Component.empty();
    }

    @Override
    public @NotNull Set<String> getNames() {
        return Set.of("papi", "placeholderapi");
    }

    @Override
    public @NotNull TagResolver getTagResolver(OfflinePlayer player) {
        return createPapiResolver(player);
    }
}
