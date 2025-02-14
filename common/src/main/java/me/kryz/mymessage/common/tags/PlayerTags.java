package me.kryz.mymessage.common.tags;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public interface PlayerTags extends BaseTag{
    @NotNull TagResolver getTagResolver(final OfflinePlayer player);
}
