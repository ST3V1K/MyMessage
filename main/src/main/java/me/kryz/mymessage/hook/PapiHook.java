package me.kryz.mymessage.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.kryz.mymessage.MyMessage;
import org.jetbrains.annotations.NotNull;

public final class PapiHook extends PlaceholderExpansion {

    private final MyMessage myMessage;

    public PapiHook(MyMessage myMessage) {
        this.myMessage = myMessage;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mm";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Krysz_";
    }

    @Override
    public @NotNull String getVersion() {
        return myMessage.getPluginVersion();
    }


}
