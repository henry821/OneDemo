package com.leetcode.easy;

import java.util.Arrays;

/**
 * Description 移动零 https://leetcode.cn/problems/move-zeroes/
 * Author henry
 * Date   2022/12/22
 */
public class LeetCode_283 {
    public static void main(String[] args) {
        int[] input = new int[]{0, 1, 0, 3, 12};
        moveZeroes(input);
        System.out.println(Arrays.toString(input));
    }

    public static void moveZeroes(int[] nums) {
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left++] = nums[right];
                nums[right] = temp;
            }
            right++;
        }
    }
}
