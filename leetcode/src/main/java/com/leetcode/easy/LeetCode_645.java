package com.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 错误的集合 https://leetcode.cn/problems/set-mismatch/
 * Author henry
 * Date   2022/12/4
 */
public class LeetCode_645 {
    public static void main(String[] args) {
        int[] input = new int[]{1, 1};
        int[] result = findErrorNums(input);
        System.out.println(Arrays.toString(result));
    }

    public static int[] findErrorNums(int[] nums) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int count = map.getOrDefault(num, 0);
            map.put(num, count + 1);
        }
        for (int i = 1; i <= nums.length; i++) {
            int count = map.getOrDefault(i, 0);
            if (count == 0) {
                result[1] = i;
            } else if (count == 2) {
                result[0] = i;
            }
        }
        return result;
    }
}
