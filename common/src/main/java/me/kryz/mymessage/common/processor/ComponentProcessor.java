package me.kryz.mymessage.common.processor;

import me.kryz.mymessage.common.papi.PAPIHook;
import me.kryz.mymessage.common.tags.BaseTag;
import me.kryz.mymessage.common.tags.PlayerTags;
import me.kryz.mymessage.common.tags.TagsRegistration;
import me.kryz.mymessage.common.util.Prefix;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ComponentProcessor {

    protected static final MiniMessage MINI_MESSAGE;
    protected static final GsonComponentSerializer GSON_COMPONENT_SERIALIZER = GsonComponentSerializer.gson();

    static {
        MINI_MESSAGE = MiniMessage.builder().tags(TagResolver.empty()).build();
    }

    private static TagResolver resolvers(final OfflinePlayer player) {
        final List<TagResolver> playerResolvers = new ArrayList<>();

        for (final BaseTag tag : BaseTag.all()) {
            if (tag instanceof PlayerTags playerTag) {
                playerResolvers.add(playerTag.getTagResolver(player));
            }
        }

        return TagResolver.builder()
                .resolvers(playerResolvers)
                .build();
    }

    public static net.kyori.adventure.text.Component asMiniMessage(final String text, final Player player){
        return MINI_MESSAGE.deserialize(text,
                resolvers(player),
                TagsRegistration.register());
    }

    public static net.kyori.adventure.text.Component asMiniMessage(final String text){
        return MINI_MESSAGE.deserialize(text,
                TagsRegistration.register());
    }

//    public static net.kyori.adventure.text.Component asMiniMessage(final String text, final OfflinePlayer player){
//        if(player.isOnline()){
//            return asMiniMessage(text, player);
//        }
//        return MINI_MESSAGE.deserialize(text,
//                PapiTag.createPapiResolver(player),
//                TagsRegistration.register());
//    }

    public static Component textProcessor(final String text, final Player player){
        final var removed = PAPIHook.formatString(removeFirstSequence(text), player);
        return ComponentProcessor.asMiniMessage(removed, player);
    }

    private static String removeFirstSequence(String message) {
        int index = Prefix.getPrefix().length();
        return message.substring(index);
    }
}
