package com.leetcode.normal;

/**
 * Description 跳跃游戏 II https://leetcode.cn/problems/jump-game-ii/
 * Author henry
 * Date   2023/2/5
 */
public class LC_45 {

    public static void main(String[] args) {
        int[] input = new int[]{2, 3, 1, 1, 4};
        System.out.println(jump(input));
    }

    public static int jump(int[] nums) {
        int furthest = 0;
        int end = 0; //end记录跳跃的最远距离的索引
        int steps = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            furthest = Math.max(furthest, i + nums[i]);
            if (i == end) {
                end = furthest;
                steps++;
            }
        }
        return steps;
    }

}
