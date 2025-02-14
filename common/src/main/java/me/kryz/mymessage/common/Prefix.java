package me.kryz.mymessage.common;

public final class Prefix {
    private static String prefix;

    public Prefix() {
    }

    public static void setPrefix(String prefix) { Prefix.prefix = prefix;}

    public static boolean startsWith(String message) {
        return message.startsWith(prefix);
    }

    public static String getPrefix() {
        return prefix == null ? "$" : prefix;
    }
}
