package com.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和 https://leetcode-cn.com/problems/3sum/
 */
public class ThreeSum {

    public static void main(String[] args) {

        int[] nums = {-1, 0, 1, 2, -1, -4};

        ThreeSum method = new ThreeSum();
        List<List<Integer>> result = method.threeSum(nums);
        for (List<Integer> list : result) {
            for (Integer integer : list) {
                System.out.print(integer + ",");
            }
            System.out.println();
        }

    }

    /**
     * 先排序，然后使用双指针
     *
     * @param nums 输入数组
     * @return 输出数组
     */
    List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) { //如果当前数字大于0，说明与其后面的数相加一定大于0，则提前退出循环。
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) { //去重
                continue;
            }
            int m = i + 1; //左指针
            int n = nums.length - 1; //右指针
            while (m < n) {
                int temp = nums[i] + nums[m] + nums[n];
                if (temp == 0) { //如果三数之和为0，则保存结果
                    result.add(Arrays.asList(nums[i], nums[m], nums[n]));
                    while (m < n && nums[m + 1] == nums[m]) { //去重
                        m++;
                    }
                    while (m < n && nums[n - 1] == nums[n]) { //去重
                        n--;
                    }
                    m++;
                    n--;
                } else if (temp < 0) { //如果三数之和小于0，说明左指针对应的数太小，则向右移动左指针
                    m++;
                } else { //如果三数之和大于0，说明左指针对应的数太大，则向左移动右指针
                    n--;
                }
            }
        }
        return result;
    }

}
