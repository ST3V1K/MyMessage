package me.kryz.mymessage.common.tags.papi;

import me.kryz.mymessage.common.papi.PAPIHook;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.OfflinePlayer;

import java.util.Set;

public final class PapiTag {
    public static TagResolver createPapiResolver(final OfflinePlayer player) {
        return TagResolver.resolver(Set.of("papi", "placeholderapi"), (argumentQueue, context) -> {
            final String placeholder = argumentQueue.popOr("The papi tag must be a placeholder.").value();
            final String parsed = PAPIHook.formatString('%' + placeholder + '%', player);
            return Tag.preProcessParsed(parsed);
        });
    }
}
