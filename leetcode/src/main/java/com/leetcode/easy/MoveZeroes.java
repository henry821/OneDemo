package com.leetcode.easy;

/**
 * Description 移动零 https://leetcode-cn.com/problems/move-zeroes/
 * Author wanghengwei
 * Date   2019/9/17 9:54
 */
public class MoveZeroes {

    public static void main(String[] args) {
        int[] nums = {0,1};

        MoveZeroes method = new MoveZeroes();
        method.moveZeroes(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }

    private void moveZeroes(int[] nums) {
        //j:插入位置下标 ; i:查找位置下标
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                j++;
            }
        }
        //将后面的位置补0
        for (int p = j; p < nums.length; p++) {
            nums[p] = 0;
        }
    }

}
