package com.leetcode.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description 找到字符串中所有字母异位词 https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/
 * Author wanghengwei
 * Date   2019/9/19 10:06
 */
public class FindAllAnagramsInAString {

    public static void main(String[] args) {
        FindAllAnagramsInAString method = new FindAllAnagramsInAString();
        List<Integer> result = method.findAnagrams("cbaebabacd", "abc");
        for (Integer integer : result) {
            System.out.println(integer);
        }
    }

    private List<Integer> findAnagrams(String s, String p) {
        // 用数组记录答案
        List<Integer> result = new ArrayList<>();
        int left = 0, right = 0;
        HashMap<Character, Integer> needs = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            needs.put(p.charAt(i), needs.getOrDefault(p.charAt(i), 0) + 1);
        }
        int match = 0;

        while (right < s.length()) {
            char c1 = s.charAt(right);
            if (needs.containsKey(c1)) {
                window.put(c1, window.getOrDefault(c1, 0) + 1);
                if (window.get(c1).equals(needs.get(c1)))
                    match++;
            }
            right++;

            while (match == needs.size()) {
                // 如果 window 的大小合适
                // 就把起始索引 left 加入结果
                if (right - left == p.length()) {
                    result.add(left);
                }
                char c2 = s.charAt(left);
                if (needs.containsKey(c2)) {
                    window.put(c2, window.getOrDefault(c2, 0) - 1);
                    if (window.get(c2) < needs.get(c2))
                        match--;
                }
                left++;
            }
        }
        return result;
    }

}
