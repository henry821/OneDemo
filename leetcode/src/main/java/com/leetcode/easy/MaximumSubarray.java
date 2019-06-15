package com.leetcode.easy;

/**
 * 最大子序和 https://leetcode-cn.com/problems/maximum-subarray
 */
public class MaximumSubarray {

    public static void main(String[] args) {
        MaximumSubarray method = new MaximumSubarray();
        int[] array = {-2, 1 - 3, 4, -1, 2, 1, -5, 4};
        System.out.println(method.maxSubArray(array));
    }

    /**
     * 时间复杂度为O（n*2）的最普通方法
     *
     * @param nums 输入数组
     * @return 最大子序和
     */
    private int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int tempMax = nums[i];
            int sum = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                if (sum > tempMax) {
                    tempMax = sum;
                }
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }
}
