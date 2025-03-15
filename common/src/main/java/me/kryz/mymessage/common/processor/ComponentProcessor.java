package me.kryz.mymessage.common.processor;

import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.papi.PAPIHook;
import me.kryz.mymessage.common.tags.BaseTag;
import me.kryz.mymessage.common.tags.PlayerTags;
import me.kryz.mymessage.common.tags.TagsRegistration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ComponentProcessor {

    public static final MiniMessage MINI_MESSAGE;
    public static final PlainTextComponentSerializer PLAIN_TEXT_COMPONENT_SERIALIZER;
    protected static final GsonComponentSerializer GSON_COMPONENT_SERIALIZER = GsonComponentSerializer.gson();

    static {
        MINI_MESSAGE = MiniMessage.miniMessage();
        PLAIN_TEXT_COMPONENT_SERIALIZER = PlainTextComponentSerializer.plainText();
    }

    private static TagResolver resolvers(final OfflinePlayer player) {
        final TagResolver.Builder builder = TagResolver.builder();

        for (final PlayerTags tag : BaseTag.allPlayers()) {
            builder.resolver(tag.getTagResolver(player));
        }

        return builder.build();
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

    public static net.kyori.adventure.text.Component asMiniMessage(final String text, final OfflinePlayer player){
        if(!player.isOnline()) return asMiniMessage(text);
        return asMiniMessage(text, (Player) player);
    }

    public static Component process(final String json, final Player player){
        final var gson = GSON_COMPONENT_SERIALIZER.deserialize(json);
        var mini = MINI_MESSAGE.serialize(gson);
        final var builder = new StringBuilder();
        if(mini.contains("\\")) {
            for (final char c : mini.toCharArray()) {
                if (c != '\\') builder.append(c);
            }
            mini = builder.toString();
        }
        final var removed = PAPIHook.formatString(removeFirstSequence(mini), player);
        return ComponentProcessor.asMiniMessage(removed, player);
    }

    public static Component textProcessor(final String text, final Player player){
        final var removed = PAPIHook.formatString(removeFirstSequence(text), player);
        return ComponentProcessor.asMiniMessage(removed, player);
    }

    private static String removeFirstSequence(final String message) {
        return message.replaceFirst(Prefix.getPrefix(), "");
    }
}
