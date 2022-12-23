package com.leetcode.normal;

import java.util.Arrays;

/**
 * Description 非递减数列 https://leetcode.cn/problems/non-decreasing-array/
 * Author henry
 * Date   2022/12/22
 */
public class LeetCode_665 {

    public static void main(String[] args) {
        int[] input = new int[]{3, 4, 2, 3};
        boolean result = checkPossibility(input);
        System.out.println(result);
    }

    public static boolean checkPossibility(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            int current = nums[i];
            int next = nums[i + 1];
            if (current > next) {
                count++;
                if (count > 1) {
                    return false;
                }
                if (i > 0) {
                    int pre = nums[i - 1];
                    if (next < pre) {
                        nums[i + 1] = current;
                    }
                }
            }
            System.out.println(i + " " + count + " " + Arrays.toString(nums));
        }
        return true;
    }

}
