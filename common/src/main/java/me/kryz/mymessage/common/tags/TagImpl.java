package me.kryz.mymessage.common.tags;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Modifying;
import org.jetbrains.annotations.NotNull;

public final class TagImpl implements Modifying {

    private final BaseTag tag;

    public TagImpl(final BaseTag tag) {
        this.tag = tag;
    }

    @Override
    public Component apply(@NotNull Component current, int depth) {
        if (depth == 0) {
            return this.tag.process(current);
        }
        return Component.empty();
    }
}
