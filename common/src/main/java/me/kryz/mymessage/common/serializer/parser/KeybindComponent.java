package me.kryz.mymessage.common.serializer.parser;

public final class KeybindComponent extends MinecraftComponent {
    private final String keybind;

    public KeybindComponent(String keybind) {
        this.keybind = keybind;
    }

    @Override
    public String toMini() {
        return "<key:" + keybind + ">";
    }

    @Override
    public String toPlain() {
        return "";
    }
}
