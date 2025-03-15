package me.kryz.mymessage.common.serializer.parser;

public final class ClickEventComponent extends MinecraftComponent {
    private final MinecraftComponent component;
    private final String action;
    private final String value;

    public ClickEventComponent(MinecraftComponent component, String action, String value) {
        this.component = component;
        this.action = action;
        this.value = value;
    }

    @Override
    public String toMini() {
        return "<click:" + action + ":'" + value + "'>" + component.toMini() + "</click>";
    }

    @Override
    public String toPlain() {
        return component.toPlain();
    }
}