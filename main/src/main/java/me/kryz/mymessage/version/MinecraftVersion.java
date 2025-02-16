package me.kryz.mymessage.version;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum MinecraftVersion {
//    v1_20_R2("1.20.2", "v1_20_R2"),
    v1_20_R3("1.20.3, 1.20.4", "v1_20_R3"),
    v1_20_R5("1.20.5, 1.20.6", "v1_20_R5"),
    v1_21_R1("1.21.0, 1.21.1", "v1_21_R1"),
    v1_21_R2("1.21.2, 1.21.3", "v1_21_R2"),
    v1_21_R3("1.21.4", "v1_21_R3"),
    UNSUPPORTED("-", "Unsupported");

    private final String version;

    private final String formattedVersion;

    MinecraftVersion(final String version, final String formattedVersion){
        this.version = version;
        this.formattedVersion = formattedVersion;
    }

    @NotNull
    public static MinecraftVersion getServerVersionEnum(){
        for (final MinecraftVersion ver : MinecraftVersion.values()){
            if(ver.getVersion().contains(VersionManager.getMinecraftVersion())){
                return ver;
            }
        }
        return UNSUPPORTED;
    }

    @NotNull
    public static String getFormatted(){
        final MinecraftVersion ver = getServerVersionEnum();
        return ver.getFormattedVersion();
    }
}
