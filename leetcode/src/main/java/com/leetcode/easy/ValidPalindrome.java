package com.leetcode.easy;

/**
 * 验证回文串 https://leetcode-cn.com/problems/valid-palindrome/
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        ValidPalindrome method = new ValidPalindrome();
        System.out.println(method.isPalindrome("A man, a plan, a canal: Panama"));
    }

    private boolean isPalindrome(String s) {
        if (s.isEmpty()) {
            return true;
        }
        String realStr = s.toLowerCase().trim();
        System.out.println(realStr);
        int i = 0;
        int j = realStr.length() - 1;
        while (i <= j) {
            char c1 = realStr.charAt(i);
            char c2 = realStr.charAt(j);
            System.out.println(i + "->" + c1 + " , " + j + "->" + c2);
            if (c1 < 48 || c1 > 122 || c1 > 57 && c1 < 97) {
                i++;
                continue;
            }
            if (c2 < 48 || c2 > 122 || c2 > 57 && c2 < 97) {
                j--;
                continue;
            }
            if (c1 != c2) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
