package com.leetcode.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Description 第三大的数 https://leetcode.cn/problems/third-maximum-number/
 * Author henry
 * Date   2022/11/23
 */
public class LeetCode_414 {

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 3, 2, 1};
        int result = thirdMax(nums);
        System.out.println(result);
    }

    public static int thirdMax(int[] nums) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        for (int num : nums) {
            treeSet.add(num);
            if (treeSet.size() > 3) {
                treeSet.remove(treeSet.first());
            }
        }
        return treeSet.size() == 3 ? treeSet.first() : treeSet.last();
    }

}
