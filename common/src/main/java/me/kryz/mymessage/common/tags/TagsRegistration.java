package me.kryz.mymessage.common.tags;

import me.kryz.mymessage.common.papi.PAPIHook;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.OfflinePlayer;

public final class TagsRegistration {

    public static final TagResolver RESOLVER;

    static {
        final TagResolver.Builder builder = TagResolver.builder();
        for(BaseTag tag : BaseTag.all()){
            builder.tag(tag.getNames(), tag.getFunction(new TagImpl(tag)));
        }
        RESOLVER = builder.build();
    }

    public static TagResolver register() {
        return TagResolver.builder().resolvers(
                RESOLVER,
                StandardTags.defaults()
        ).build();
    }
}
