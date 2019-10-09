package com.leetcode.normal;

/**
 * Description 盛最多水的容器 https://leetcode-cn.com/problems/container-with-most-water/
 * Author wanghengwei
 * Date   2019/10/9 10:16
 */
public class LeetCode_11 {

    public static void main(String[] args) {
        LeetCode_11 method = new LeetCode_11();
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(method.maxArea(height));
    }

    private int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int max = 0;
        while (i <= j) {
            int left = height[i];
            int right = height[j];
            //如果左指针容量小于右指针容量，则（左指针容量*左右指针间隔），然后左指针+1
            //如果右指针容量小于左指针容量，则（右指针容量*左右指针间隔），然后右指针-1
            max = Math.max(max, left < right ? left * (j - i++) : right * (j-- - i));
        }
        return max;
    }

}
