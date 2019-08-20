package com.leetcode.normal;

/**
 * 最长回文字串 https://leetcode-cn.com/problems/longest-palindromic-substring/
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        LongestPalindromicSubstring method = new LongestPalindromicSubstring();
        System.out.println(method.longestPalindrome("ababa"));
    }

    private String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int l = left;
        int r = right;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        //举例：s=ababa,left=0,right=0       s=abcbbb,left=3,right=4
        //则此时：l=-1,r=1                    l=2,r=5
        //所以最终结果：r-l-1=1                r-l-1=2
        return r - l - 1;
    }

}
