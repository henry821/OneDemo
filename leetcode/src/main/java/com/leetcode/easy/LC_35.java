package com.leetcode.easy;

/**
 * Description 搜索插入位置 https://leetcode.cn/problems/search-insert-position/
 * Author henry
 * Date   2023/3/7
 */
public class LC_35 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 5, 6};
        int target = 7;
        System.out.println(searchInsert(nums, target));
    }

    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int midValue = nums[mid];
            if (midValue == target) {
                return mid;
            }
            if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return target > nums[left] ? left + 1 : left;
    }

}
