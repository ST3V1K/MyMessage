package me.kryz.mymessage;

import lombok.Getter;
import me.kryz.mymessage.common.Prefix;
import me.kryz.mymessage.common.tags.BaseTag;
import me.kryz.mymessage.listeners.PlayerJoinListener;
import me.kryz.mymessage.loader.NMSLoader;
import me.kryz.mymessage.logger.MyLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MyMessage extends JavaPlugin {

    @Getter
    public static boolean isPaper = false;

    private NMSLoader loader;

    @Override
    public void onEnable() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            isPaper = true;
        } catch (final ClassNotFoundException e) {
            isPaper = false;
        }

        BaseTag.defaults();
        loader = new NMSLoader(this);
        loader.load();

        saveDefaultConfig();
        loadConfig();

        loader.getPacketRegister().register(this);

        MyLogger.getLogger().info("Register MyMessage NMS packets.");

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(loader.getAdaptation(), this);
    }

    public void loadConfig() {
        reloadConfig();
        final String prefix = this.getConfig().getString("prefix");
        Prefix.setPrefix(prefix);
    }

    public String getPluginVersion() {
        if (isPaper()) {
            return this.getPluginMeta().getVersion();
        } else {
            return this.getDescription().getVersion();
        }
    }
}
