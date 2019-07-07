package com.leetcode.hard;

/**
 * 缺失的第一个正数 https://leetcode-cn.com/problems/first-missing-positive/
 */
public class FirstMissingPositive {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 3, 4, 5};
        FirstMissingPositive method = new FirstMissingPositive();
        int i = method.firstMissingPositive(nums);
        System.out.println(i);
    }

    private int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            while (nums[i] > 0 //当前索引位置的数字大于0
                    && nums[i] <= len //当前索引位置的数字小于等于数组长度
                    && nums[i] != nums[nums[i] - 1] //当前索引数字不在它应该在的位置上
            ) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for (int i = 0; i < len; i++) {
            if (i != nums[i] - 1) {
                return i + 1;
            }
        }
        return len + 1;
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}
