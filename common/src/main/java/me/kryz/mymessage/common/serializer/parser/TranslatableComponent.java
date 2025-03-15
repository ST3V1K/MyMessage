package me.kryz.mymessage.common.serializer.parser;

import java.util.ArrayList;
import java.util.List;

public final class TranslatableComponent extends MinecraftComponent {
    private final String translate;
    private final List<String> with;

    public TranslatableComponent(String translate, List<String> with) {
        this.translate = translate;
        this.with = with == null ? new ArrayList<>() : with;
    }

    @Override
    public String toMini() {
        StringBuilder builder = new StringBuilder();
        builder.append("<lang:").append(translate);
        for (final String component : with) {
            builder.append(":'").append(component).append("'");
        }
        builder.append(">");
        return builder.toString();
    }
    @Override
    public String toPlain() {
        return translate;
    }
}
