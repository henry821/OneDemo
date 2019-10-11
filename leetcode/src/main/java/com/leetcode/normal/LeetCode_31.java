package com.leetcode.normal;

/**
 * Description 下一个排列  https://leetcode-cn.com/problems/next-permutation/
 * Author wanghengwei
 * Date   2019/10/11 17:46
 */
public class LeetCode_31 {

    public static void main(String[] args) {

        LeetCode_31 method = new LeetCode_31();

        int[] nums = {1, 5, 8, 4, 7, 6, 5, 3, 1};

        method.nextPermutation(nums);

    }

    private void nextPermutation(int[] nums) {
        printArray(nums);
        int i = nums.length - 2;
        //从数组结尾开始查找nums[i+1]<=nums[i]的情况，i就是需要交换的第一个位置
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        System.out.println("需要交换的第一个位置：i = " + i + " ,nums[i] = " + nums[i]);
        if (i >= 0) {
            int j = nums.length - 1;
            //从数组结尾开始查找第一个比nums[i]大的数字nums[j]，j就是需要交换的第二个位置
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            System.out.println("需要交换的第二个位置：j = " + j + " ,nums[j] = " + nums[j]);
            //交换i和j位置的数字
            swap(nums, i, j);
        }
        printArray(nums);
        reverse(nums, i + 1);
        printArray(nums);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void printArray(int[] nums) {
        System.out.print("当前数组：");
        for (int num : nums) {
            System.out.print(num + ",");
        }
        System.out.println();
    }

}
