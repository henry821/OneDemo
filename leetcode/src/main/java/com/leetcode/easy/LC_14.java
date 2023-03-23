package com.leetcode.easy;

/**
 * Description 最长公共前缀 https://leetcode.cn/problems/longest-common-prefix/
 * Author henry
 * Date   2023/3/21
 */
public class LC_14 {

    public static void main(String[] args) {
        String[] strs = new String[]{"dog","racecar","car"};
        System.out.println(longestCommonPrefix(strs));
    }

    public static String longestCommonPrefix(String[] strs) {
        int index = 0;
        StringBuilder result = new StringBuilder();
        while (true) {
            char tmp = 0;
            boolean quit = false;
            for (String str : strs) {
                if (index == str.length()) {
                    quit = true;
                    break;
                }
                char c = str.charAt(index);
                if (tmp == 0) {
                    tmp = c;
                } else {
                    if (tmp != c) {
                        quit = true;
                        break;
                    }
                }
            }
            if (quit) {
                break;
            }
            result.append(tmp);
            index++;
        }
        return result.toString();
    }

}
