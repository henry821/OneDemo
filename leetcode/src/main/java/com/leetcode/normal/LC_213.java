package com.leetcode.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description 打家劫舍 II https://leetcode.cn/problems/house-robber-ii/
 * Author henry
 * Date   2023/2/6
 */
public class LC_213 {

    public static void main(String[] args) {
        int[] input = new int[]{1, 3, 1, 3, 100};
        System.out.println(rob(input));
    }

    public static int rob(int[] nums) {
        HashMap<Boolean, List<Integer>> dp = new HashMap<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list1.add(-1);
            list2.add(-1);
        }
        dp.put(true, list1);
        dp.put(false, list2);
        return robDp(nums, dp, 0, false);
//        return robNormal(nums, 0, false);
    }

    /**
     * 无缓存版本
     * 从[index]号房屋开始可以偷到的最大价值
     * [fromFirst]表示是否偷了第一间房屋，是则不能偷最后一间房屋
     **/
    public static int robNormal(int[] nums, int index, boolean fromFirst) {
        //退出条件
        if (index > nums.length - 1) {
            return 0;
        }
        //如果偷了第一间房屋，则不能偷最后一间房屋
        if (index == nums.length - 1) {
            return fromFirst ? 0 : nums[index];
        }
        int value1;
        int value2;
        if (index == 0) {
            //偷当前房屋
            value1 = nums[index] + robNormal(nums, index + 2, true);
            //不偷当前房屋
            value2 = robNormal(nums, index + 1, false);
        } else {
            //偷当前房屋
            value1 = nums[index] + robNormal(nums, index + 2, fromFirst);
            //不偷当前房屋
            value2 = robNormal(nums, index + 1, fromFirst);
        }
        return Math.max(value1, value2);
    }

    /**
     * 有缓存版本
     * 从[index]号房屋开始可以偷到的最大价值
     * [fromFirst]表示是否偷了第一间房屋，是则不能偷最后一间房屋
     **/
    public static int robDp(int[] nums, HashMap<Boolean, List<Integer>> dp, int index, boolean fromFirst) {
        //退出条件
        if (index > nums.length - 1) {
            return 0;
        }
        //如果偷了第一间房屋，则不能偷最后一间房屋
        if (index == nums.length - 1) {
            return fromFirst ? 0 : nums[index];
        }
        List<Integer> list = dp.get(fromFirst);
        Integer value = list.get(index);
        if (value > -1) {
            return value;
        }
        //偷当前房屋
        int value1 = nums[index] + robDp(nums, dp, index + 2, index == 0 || fromFirst);
        //不偷当前房屋
        int value2 = robDp(nums, dp, index + 1, index != 0 && fromFirst);

        int result = Math.max(value1, value2);
        list.set(index, result);
        dp.put(fromFirst, list);
        return result;
    }

}
