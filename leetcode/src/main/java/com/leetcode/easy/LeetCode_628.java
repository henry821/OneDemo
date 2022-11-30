package com.leetcode.easy;

/**
 * Description 三个数的最大乘积 https://leetcode.cn/problems/maximum-product-of-three-numbers/
 * Author henry
 * Date   2022/11/30
 */
public class LeetCode_628 {
    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 3, 4};
        int result = maximumProduct(input);
        System.out.println(result);
    }

    public static int maximumProduct(int[] nums) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for (int x : nums) {
            if (x < min1) {
                min2 = min1;
                min1 = x;
            } else if (x < min2) {
                min2 = x;
            }
            if (x > max3) {
                max1 = max2;
                max2 = max3;
                max3 = x;
            } else if (x > max2) {
                max1 = max2;
                max2 = x;
            } else if (x > max1) {
                max1 = x;
            }
        }
        return Math.max(max1 * max2 * max3, min1 * min2 * max3);
    }
}
