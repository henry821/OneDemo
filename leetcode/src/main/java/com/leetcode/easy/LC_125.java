package com.leetcode.easy;

import java.util.Locale;

/**
 * Description 验证回文串 https://leetcode.cn/problems/valid-palindrome/
 * Author henry
 * Date   2023/3/21
 */
public class LC_125 {

    public static void main(String[] args) {
        String s = "";
        System.out.println(isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {
        char[] chars = s.toLowerCase(Locale.ROOT).toCharArray();
        System.out.println(chars);
        int l = 0;
        int r = chars.length - 1;
        while (l <= r) {
            char cl = chars[l];
            char cr = chars[r];

            //跳过逗号、空格等无关字符
            if (!Character.isLetterOrDigit(cl)) {
                l++;
                continue;
            }

            if (!Character.isLetterOrDigit(cr)) {
                r--;
                continue;
            }

            //比较字符
            if (cl != cr) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

}
