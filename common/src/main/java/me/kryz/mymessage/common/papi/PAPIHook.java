package me.kryz.mymessage.common.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

public final class PAPIHook {

    public static String formatString(final String text, final OfflinePlayer player) {
        return PlaceholderAPI.setPlaceholders(player, text);
    }

}
