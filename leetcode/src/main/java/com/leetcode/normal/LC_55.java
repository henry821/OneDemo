package com.leetcode.normal;

/**
 * Description 跳跃游戏 https://leetcode.cn/problems/jump-game/
 * Author henry
 * Date   2023/1/31
 */
public class LC_55 {

    public static void main(String[] args) {
        int[] input = new int[]{2, 3, 1, 1, 4};
        boolean result = canJump(input);
        System.out.println(result);
    }

    public static boolean canJump(int[] nums) {
        int furthest = 0;
        for (int i = 0; i < nums.length; i++) {
            int step = nums[i];
            if (i <= furthest) {
                furthest = Math.max(furthest, i + step);
                if (furthest >= nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }


}
