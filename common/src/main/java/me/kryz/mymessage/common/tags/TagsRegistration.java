package me.kryz.mymessage.common.tags;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public final class TagsRegistration {

    private static final TagResolver RESOLVER;

    static {
        final TagResolver.Builder builder = TagResolver.builder();

        for (final NormalTags tag : BaseTag.allNormal()) {
            builder.tag(tag.getNames(), tag.getFunction(new TagImpl(tag)));
        }

        RESOLVER = builder.build();
    }

    public static TagResolver register() {
        return TagResolver.builder()
                .resolver(RESOLVER)
//                .resolver(StandardTags.hoverEvent())
//                .resolver(StandardTags.clickEvent())
//                .resolver(StandardTags.translatable())
//                .resolver(StandardTags.translatableFallback())
//                .resolver(StandardTags.defaults())
                .build();
    }

    private TagsRegistration() {
    }
}
