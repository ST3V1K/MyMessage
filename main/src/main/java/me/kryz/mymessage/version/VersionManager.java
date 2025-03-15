package me.kryz.mymessage.version;

import lombok.Getter;
import me.kryz.mymessage.MyMessage;

import org.bukkit.Bukkit;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class VersionManager {
    @Getter
    private static String version;
    @Getter
    private static final String minecraftVersion;

//    private static final Map<String, Integer> VERSION_MAP = new HashMap<>();

    static {
//        register("1.20", 1200);
//        register("1.20.1", 1201);
//        register("1.20.2", 1202);
//        register("1.20.3", 1203);
//        register("1.20.4", 1204);
//        register("1.20.5", 1205);
//        register("1.20.6", 1206);
//        register("1.21", 1210);
//        register("1.21.1", 1211);
//        register("1.21.2", 1212);
//        register("1.21.3", 1213);
//        register("1.21.4", 1214);
//        register("1.21.5", 1215);
        minecraftVersion = Bukkit.getServer().getMinecraftVersion();
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (Exception e) {
            version = MyMessage.isPaper() ? "paper" : "unknown-server";
        }
    }
//
//    private static void register(String version, int value) {
//        VERSION_MAP.put(version, value);
//    }

    public static String getRev() {
        return getVersion();
    }

    public static boolean isValidVersionSet() {
        return minecraftVersion != null;
    }

    public static boolean isVersion(final MinecraftVersion minecraftVersion){
        return MinecraftVersion.getServerVersionEnum() == minecraftVersion;
    }
}