package me.kryz.mymessage.common.serializer.parser;

public final class HoverEventComponent extends MinecraftComponent {
    private final MinecraftComponent component;
    private final String action;
    private final String contents;

    public HoverEventComponent(MinecraftComponent component, String action, String contents) {
        this.component = component;
        this.action = action;
        this.contents = contents;
    }

    @Override
    public String toMini() {
        return "<hover:" + action + ":'" + contents + "'>" + component.toMini() + "</hover>";
    }

    @Override
    public String toPlain() {
        return "";
    }
}