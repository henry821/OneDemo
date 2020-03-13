package com.leetcode.easy;

/**
 * Description 多数元素 https://leetcode-cn.com/problems/majority-element/
 * Author wanghengwei
 * Date   2020/3/13
 */
public class LeetCode_169 {

    public static void main(String[] args) {

        int[] nums = {6, 5, 5};
        LeetCode_169 method = new LeetCode_169();
        int majorityElement = method.majorityElement(nums);
        System.out.println(majorityElement);

    }

    /**
     * 分治算法
     */
    public int majorityElement(int[] nums) {
        return getDividedArrayMajorityElement(nums, 0, nums.length - 1);
    }

    /**
     * 获取子数组多数
     *
     * @param nums  数组
     * @param left  左下标
     * @param right 右下标
     * @return 当前数组多数
     */
    public int getDividedArrayMajorityElement(int[] nums, int left, int right) {
        //退出条件，如果数组里只有一个元素，则返回当前元素(当前元素是数组里的多数)
        if (left == right) {
            return nums[left];
        }
        //获取中间元素下标
        int mid = (left + right) / 2;
        //获取左右两个子数组的多数(递归)
        int leftMajorityElement = getDividedArrayMajorityElement(nums, left, mid);
        int rightMajorityElement = getDividedArrayMajorityElement(nums, mid + 1, right);

        //比较左右两个子数组的多数在此数组的具体出现次数，返回出现次数多的多数作为此数组的多数
        int leftMajorityElementCount = getMajorityElementCount(nums, leftMajorityElement, left, right);
        int rightMajorityElementCount = getMajorityElementCount(nums, rightMajorityElement, left, right);

        return rightMajorityElementCount > leftMajorityElementCount ?
                rightMajorityElement : leftMajorityElement;

    }

    /**
     * 获取区间内多数的具体出现次数
     *
     * @param nums            数组
     * @param majorityElement 多数
     * @param left            左下标
     * @param right           右下标
     * @return 多数出现次数
     */
    public int getMajorityElementCount(int[] nums, int majorityElement, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (nums[i] == majorityElement) {
                count++;
            }
        }
        return count;
    }

}
