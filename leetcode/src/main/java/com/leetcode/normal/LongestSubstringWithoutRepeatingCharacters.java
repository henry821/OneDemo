package com.leetcode.normal;

import java.util.HashMap;

/**
 * 无重复子串的最长子串 https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {

        String input = "pwwkew";

        LongestSubstringWithoutRepeatingCharacters method = new LongestSubstringWithoutRepeatingCharacters();
        int result = method.lengthOfLongestSubstring(input);
        System.out.println(result);

    }

    /**
     * 滑动窗口
     *
     * @param s 给定字符串
     * @return 不含有重复字符的最长子串的长度
     */
    private int lengthOfLongestSubstring(String s) {
        int left = 0;//窗口左边界
        String curLongestSubstring = ""; //当前最长子串
        HashMap<Character, Integer> hashMap = new HashMap<>(); //存储当前子串字符
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = s.charAt(i); //拿到当前字符
            if (hashMap.containsKey(c)) { //如果当前字符与子串冲突，则移动窗口左边界至子串中冲突字符的下一位
                while (chars[left] != c) {
                    hashMap.remove(chars[left]);
                    left++;
                }
                left++;
            }
            hashMap.put(c, i);
            String substring = s.substring(left, i + 1);
            if (substring.length() > curLongestSubstring.length()) {
                curLongestSubstring = substring;
            }
        }
        return curLongestSubstring.length();
    }

}
