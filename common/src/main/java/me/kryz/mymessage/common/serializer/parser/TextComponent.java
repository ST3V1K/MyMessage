package me.kryz.mymessage.common.serializer.parser;

import java.util.ArrayList;
import java.util.List;

public final class TextComponent extends MinecraftComponent {
    private final String text;
    private final String color;
    private final Boolean bold;
    private final Boolean italic;
    private final Boolean underlined;
    private final Boolean strikethrough;
    private final Boolean obfuscated;
    private final List<String> extraComponents;

    // Constructor modificado para incluir la lista de extras
    public TextComponent(String text, String color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, List<String> extraComponents) {
        this.text = text;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.extraComponents = extraComponents != null ? extraComponents : new ArrayList<>();
    }

    @Override
    public String toMini() {
        final StringBuilder builder = new StringBuilder();
        if (color != null) builder.append("<").append(color).append(">");
        if (bold != null) builder.append(bold ? "<bold>" : "<!b>");
        if (italic != null) builder.append(italic ? "<italic>" : "<!i>");
        if (underlined != null) builder.append(underlined ? "<underlined>" : "<!u>");
        if (strikethrough != null) builder.append(strikethrough ? "<strikethrough>" : "<!st>");
        if (obfuscated != null) builder.append(obfuscated ? "<obfuscated>" : "<!obf>");

        builder.append(text);

        for (String extra : extraComponents) {
            builder.append(extra);
        }

        if (obfuscated != null && obfuscated) builder.append("</obfuscated>");
        if (strikethrough != null && strikethrough) builder.append("</strikethrough>");
        if (underlined != null && underlined) builder.append("</underlined>");
        if (italic != null && italic) builder.append("</italic>");
        if (bold != null && bold) builder.append("</bold>");
        if (color != null) builder.append("</").append(color).append(">");

        return builder.toString();
    }

    @Override
    public String toPlain() {
        final StringBuilder plainText = new StringBuilder(text);
        for (String extra : extraComponents) {
            plainText.append(extra);
        }
        return plainText.toString();
    }
}
