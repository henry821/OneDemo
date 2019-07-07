package com.leetcode.easy;

import java.util.HashMap;

/**
 * 求众数 https://leetcode-cn.com/problems/majority-element/
 */
public class MajorityElement {

    public static void main(String[] args) {
        int[] nums = {3, 2, 3};

        MajorityElement method = new MajorityElement();
        int result = method.majorityElement(nums);
        System.out.println(result);
    }

    /**
     * 解法1：借助散列表，时间复杂度O(n),空间复杂度O(n)
     *
     * @param nums 输入数组
     * @return 结果
     */
    int majorityElement(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer count = hashMap.get(nums[i]);
            if (count == null) {
                hashMap.put(nums[i], 1);
            } else {
                hashMap.put(nums[i], ++count);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            Integer count = hashMap.get(nums[i]);
            if (count > nums.length / 2) {
                return nums[i];
            }
        }
        return -1;
    }

}
