package me.kryz.mymessage.common.tags;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

public interface NormalTags extends BaseTag {
    
    @NotNull BiFunction<ArgumentQueue, Context, Tag> getFunction(final TagImpl tag);
}
