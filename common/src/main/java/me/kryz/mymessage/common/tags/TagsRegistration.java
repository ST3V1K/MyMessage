package me.kryz.mymessage.common.tags;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

public final class TagsRegistration {

    private static final TagResolver RESOLVER;

    static {
        BaseTag.defaults();
        final TagResolver.Builder builder = TagResolver.builder();

        for (final BaseTag tag : BaseTag.all()) {
            if (tag instanceof NormalTags normalTag) {
                builder.tag(tag.getNames(), normalTag.getFunction(new TagImpl(tag)));
            }
        }

        RESOLVER = builder.build();
    }

    public static TagResolver register() {
        return TagResolver.builder()
            .resolver(RESOLVER)
            .resolver(StandardTags.defaults())
            .build();
    }

    private TagsRegistration() {
    }
}
