package com.leetcode.easy;

import java.util.Arrays;

/**
 * Description 反转字符串 https://leetcode.cn/problems/reverse-string/
 * Author henry
 * Date   2023/3/21
 */
public class LC_344 {

    public static void main(String[] args) {
        char[] s = new char[]{'h', 'e', 'l', 'l', 'o'};
        reverseString(s);
        System.out.println(Arrays.toString(s));
    }

    public static void reverseString(char[] s) {
        int l = 0;
        int r = s.length - 1;
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
