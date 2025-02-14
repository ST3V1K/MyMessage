package me.kryz.mymessage.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public final class MyAdventureUtils {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static BukkitAudiences audiences;

    MyAdventureUtils(final BukkitAudiences bukkitAudiences){
        audiences = bukkitAudiences;
    }

    public static @NotNull Audience player(final Player player){
        return audiences.player(player);
    }

    public static @NotNull Audience player(final UUID playerId){
        return audiences.player(playerId);
    }

    public static @NotNull Audience players(){
        return audiences.players();
    }

    public static @NotNull Audience console(){
        return audiences.console();
    }

    public static @NotNull Audience sender(final CommandSender sender){
        return audiences.sender(sender);
    }

    public static @NotNull Audience all(){
        return audiences.all();
    }

    public static void broadcast(final Collection<? extends Player> players, final Component message){
        for(final Player player : players){
            player(player).sendMessage(message);
        }
    }

    public static void broadcastToAll(final Component message){
        players().sendMessage(message);
    }

    public static Component miniDeserialize(final String format){
        return MINI_MESSAGE.deserialize(format);
    }
}
