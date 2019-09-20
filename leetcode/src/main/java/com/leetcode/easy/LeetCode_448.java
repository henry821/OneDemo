package com.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 找到所有数组中消失的数字 https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/
 * Author wanghengwei
 * Date   2019/9/20 14:20
 */
public class LeetCode_448 {

    public static void main(String[] args) {

        LeetCode_448 method = new LeetCode_448();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> result = method.findDisappearedNumbers(nums);
        System.out.println(result.toString());

    }

    private List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // 如果数组当前位置数字不在它应该在的位置，则一直进行while循环
            // 比如 nums[0]=4 ，则此数字应该在的位置下标为 nums[0] - 1 = 3
            // 两者不一致，则交换0和3位置的数字，把4放在它应该在的位置(nums[3])
            while (nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        //遍历数组，此时每个下标对应的数字应该为 下标+1 , 如果不满足这个条件，则 下标+1 为缺失的数字
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] - 1 != i) {
                result.add(i + 1);
            }
        }
        return result;
    }

    private void swap(int[] nums, int index1, int index2) {
        // 如果两个位置的数字相同，说明此数字重复且有一个位置的数字在正确的位置上，则返回
        if (nums[index1] == nums[index2]) {
            return;
        }
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}
