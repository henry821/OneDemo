package com.leetcode.utils;

import java.util.Locale;

/**
 * Description
 * Author henry
 * Date   2023/2/7
 */
public class LogUtil {

    public static void logWithIndex(int index, String content) {
        String log = String.format(Locale.getDefault(), "%s[%d],%s", insertSpace(index * 2), index, content);
        System.out.println(log);
    }

    private static String insertSpace(int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(' ');
        }
        return result.toString();
    }

}
