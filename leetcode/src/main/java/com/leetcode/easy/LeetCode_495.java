package com.leetcode.easy;

/**
 * Description 提莫攻击 https://leetcode.cn/problems/teemo-attacking/
 * Author henry
 * Date   2022/11/23
 */
public class LeetCode_495 {

    public static void main(String[] args) {
        int[] timeSeries = new int[]{1, 2};
        int duration = 2;
        int result = findPoisonedDuration(timeSeries, duration);
        System.out.println(result);
    }


    private static int findPoisonedDuration(int[] timeSeries, int duration) {
        int sum = 0;
        for (int i = 1; i < timeSeries.length; i++) {
            int offset = timeSeries[i] - timeSeries[i - 1];
            sum += Math.min(offset, duration);
        }
        sum += duration;
        return sum;
    }

}
