package me.kryz.mymessage.version;

import org.jetbrains.annotations.NotNull;

public record MinecraftVersion(String version, String formattedVersion) {
    public static final MinecraftVersion v1_20_R1 = new MinecraftVersion("1.20, 1.20.1", "v1_20_R1");
    public static final MinecraftVersion v1_20_R2 = new MinecraftVersion("1.20.2", "v1_20_R2");
    public static final MinecraftVersion v1_20_R3 = new MinecraftVersion("1.20.3, 1.20.4", "v1_20_R3");
    public static final MinecraftVersion v1_20_R5 = new MinecraftVersion("1.20.5, 1.20.6", "v1_20_R5");
    public static final MinecraftVersion v1_21_R1 = new MinecraftVersion("1.21.0, 1.21.1", "v1_21_R1");
    public static final MinecraftVersion v1_21_R2 = new MinecraftVersion("1.21.2, 1.21.3", "v1_21_R2");
    public static final MinecraftVersion v1_21_R3 = new MinecraftVersion("1.21.4", "v1_21_R3");
    public static final MinecraftVersion v1_21_R5 = new MinecraftVersion("1.21.7, 1.21.8", "v1_21_R5");
    public static final MinecraftVersion UNSUPPORTED = new MinecraftVersion("-", "Unsupported");

    @NotNull
    public static MinecraftVersion getServerVersionEnum() {
        for (final MinecraftVersion ver : MinecraftVersion.values()) {
            if (ver.version().contains(VersionManager.getMinecraftVersion())) {
                return ver;
            }
        }
        return UNSUPPORTED;
    }

    public static MinecraftVersion[] values() {
        return new MinecraftVersion[]
                {
                v1_20_R1,
                v1_20_R2,
                v1_20_R3,
                v1_20_R5,
                v1_21_R1,
                v1_21_R2,
                v1_21_R3,
                v1_21_R5,
                UNSUPPORTED};
    }

    @NotNull
    public static String getFormatted() {
        final MinecraftVersion ver = getServerVersionEnum();
        return ver.formattedVersion();
    }
}
