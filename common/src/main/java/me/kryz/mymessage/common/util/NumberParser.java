package me.kryz.mymessage.common.util;

/**
 * Utility class for parsing numbers with additional validation.
 */
public class NumberParser {
    
    public static int parsePositiveInt(final String text, final int defaultValue) {
        if (text == null) {
            return defaultValue;
        }

        final int length = text.length();
        int result = 0;
        for (int i = 0; i < length; i++) {
            int value = text.charAt(i) - '0';
            if (value > 9 || value < 0) {
                return defaultValue; 
            }
            result = (result + value) * 10;
        } 
        return result / 10;
}
}
