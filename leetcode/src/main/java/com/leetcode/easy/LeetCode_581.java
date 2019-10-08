package com.leetcode.easy;

/**
 * Description 最短无序连续子数组 https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/
 * Author wanghengwei
 * Date   2019/10/8 11:02
 */
public class LeetCode_581 {

    public static void main(String[] args) {

    }

    public int findUnsortedSubarray(int[] nums) {
        int left = nums.length;
        int right = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    right = Math.max(right, j);
                    left = Math.min(left, i);
                }
            }
        }
        return right - left < 0 ? 0 : right - left + 1;
    }

}
