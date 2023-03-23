package com.leetcode.easy;

/**
 * Description 反转字符串中的元音字母 https://leetcode.cn/problems/reverse-vowels-of-a-string/
 * Author henry
 * Date   2023/3/21
 */
public class LC_345 {

    public static void main(String[] args) {
        String s = " ";
        System.out.println(reverseVowels(s));
    }

    public static String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length - 1;
        int l = 0;
        int r = length;
        while (l < r) {
            while (l < length && !isVowel(chars[l])) {
                l++;
            }
            while (r > 0 && !isVowel(chars[r])) {
                r--;
            }
            if (l < r) {
                revers(chars, l, r);
                l++;
                r--;
            }
        }
        return new String(chars);
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'
                || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    private static void revers(char[] c, int l, int r) {
        System.out.println("reverse " + l + " " + r);
        char temp = c[l];
        c[l] = c[r];
        c[r] = temp;
    }

}
