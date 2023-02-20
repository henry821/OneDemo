package com.leetcode.normal;

import java.util.Arrays;

/**
 * Description 打家劫舍 https://leetcode.cn/problems/house-robber/
 * Author henry
 * Date   2023/2/3
 */
public class LC_198 {

    public static void main(String[] args) {
        int[] input = new int[]{2, 7, 9, 3, 1};
        System.out.println(rob(input));
    }

    public static int rob(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return method(nums, dp, 0);
    }

    /**
     * 从[index]号房屋开始能偷到的最大价值
     */
    public static int method(int[] nums, int[] dp, int index) {
        //退出条件: index越界
        if (index >= nums.length) {
            return 0;
        }
        if (dp[index] > -1) {
            return dp[index];
        }
        //选择1: 偷当前房屋
        int value1 = nums[index] + method(nums, dp, index + 2);
        //选择2: 不偷当前房屋
        int value2 = method(nums, dp, index + 1);
        int result = Math.max(value1, value2);
        dp[index] = result;
        return result;
    }

}
