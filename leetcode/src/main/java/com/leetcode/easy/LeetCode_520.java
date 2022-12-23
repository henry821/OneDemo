package com.leetcode.easy;

/**
 * Description 检测大写字母 https://leetcode.cn/problems/detect-capital/
 * Author henry
 * Date   2022/12/23
 */
public class LeetCode_520 {
    public static void main(String[] args) {
        String input = "FlaG";
        boolean result = detectCapitalUse(input);
        System.out.println(result);
    }

    public static boolean detectCapitalUse(String word) {
        char[] chars = word.toCharArray();
        if (chars.length == 1) {
            return true;
        }
        char start = chars[0];
        if (start >= 'a') {
            //首字母小写，第二个字母必须小写
            //如果第二个字母为大写，则一定错误
            if (chars[1] >= 'A' && chars[1] <= 'Z') {
                return false;
            }
        }
        //从第二个字母开始，每个字母的大小写必须一致，否则错误
        for (int i = 2; i < chars.length; i++) {
            if (isLowerCase(chars[i]) != isLowerCase(chars[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }
}
