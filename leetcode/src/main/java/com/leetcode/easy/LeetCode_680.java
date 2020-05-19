package com.leetcode.easy;

/**
 * Description 验证回文字符串 Ⅱ https://leetcode-cn.com/problems/valid-palindrome-ii/
 * Author wanghengwei
 * Date   2020/5/19
 */
public class LeetCode_680 {

    public static void main(String[] args) {

        LeetCode_680 leetCode_680 = new LeetCode_680();
        boolean result = leetCode_680.validPalindrome("abab");
        System.out.println(result);

    }

    public boolean validPalindrome(String s) {
        int low = 0;
        int high = s.length() - 1;
        while (low < high) {
            char i = s.charAt(low);
            char j = s.charAt(high);
            if (i == j) {
                low++;
                high--;
            } else {
                return isPalindrome(s.substring(low + 1, high + 1)) || isPalindrome(s.substring(low, high));
            }
        }
        return true;
    }

    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
