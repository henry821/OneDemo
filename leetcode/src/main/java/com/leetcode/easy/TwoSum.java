package com.leetcode.easy;

import java.util.HashMap;

/**
 * 两数之和 https://leetcode-cn.com/problems/two-sum/
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};

        TwoSum method = new TwoSum();
        int[] result = method.twoSum2(nums, 9);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * 暴力解法，时间复杂度O（n*2）
     *
     * @param nums   整数数组
     * @param target 目标值
     * @return 结果数组
     */
    private int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            result[0] = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 空间换时间
     *
     * @param nums   整数数组
     * @param target 目标值
     * @return 结果数组
     */
    private int[] twoSum2(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer integer = hashMap.get(target - nums[i]);
            if (integer != null) {
                result[0] = integer;
                result[1] = i;
                return result;
            } else {
                hashMap.put(nums[i], i);
            }
        }
        return result;
    }
}
