package com.leetcode.easy;

import java.util.Arrays;

/**
 * Description 反转字符串 II https://leetcode.cn/problems/reverse-string-ii/
 * Author henry
 * Date   2023/3/21
 */
public class LC_541 {

    public static void main(String[] args) {
        String s = "abcdefg";
        System.out.println(reverseStr(s, 2));
    }

    public static String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i += 2 * k) {
            int l = i;
            int r = Math.min(i + k - 1, chars.length - 1);
            reverseInner(chars, l, r);
        }
        return new String(chars);
    }

    private static void reverseInner(char[] s, int l, int r) {
        while (l <= r) {
            char cl = s[l];
            char cr = s[r];
            s[l] = cr;
            s[r] = cl;
            l++;
            r--;
        }
    }

}
