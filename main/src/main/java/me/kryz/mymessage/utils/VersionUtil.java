package me.kryz.mymessage.utils;

import me.kryz.mymessage.version.VersionManager;

public class VersionUtil {

//    public static boolean isGreaterThan(String v1, String v2) {
//        return compareVersions(v1, v2) > 0;
//    }

    public static boolean isLessThanOrEqualTo(final String v2) {
        return compareVersions(VersionManager.getMinecraftVersion(), v2) <= 0;
    }

//    public static boolean isLessThan(String v1, String v2) {
//        return compareVersions(v1, v2) < 0;
//    }
//
//    public static boolean isEqualTo(String v1, String v2) {
//        return compareVersions(v1, v2) == 0;
//    }

    private static int compareVersions(String v1, String v2) {
        final String[] parts1 = v1.split("\\.");
        final String[] parts2 = v2.split("\\.");

        final int length = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < length; i++) {
            final int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            final int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;

            if (num1 != num2) {
                return Integer.compare(num1, num2);
            }
        }
        return 0;
    }
}
