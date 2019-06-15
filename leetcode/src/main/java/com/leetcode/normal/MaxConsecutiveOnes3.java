package com.leetcode.normal;

/**
 * 最大连续1的个数III https://leetcode-cn.com/problems/max-consecutive-ones-iii
 */
public class MaxConsecutiveOnes3 {

    public static void main(String[] args) {
        int[] A = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        MaxConsecutiveOnes3 method = new MaxConsecutiveOnes3();
        System.out.println(method.longestOnes(A, 2));
    }

    /**
     * 滑动窗口解法
     *
     * @param A 由0或1组成的数组
     * @param K 可翻转个数
     * @return 子数组最大连续1的个数
     */
    private int longestOnes(int[] A, int K) {
        int numOfZero = 0; //当前0的个数
        int curMax = 0; //当前最大连续个数
        int left = 0; //窗口左边界,窗口右边界为i值，一直向后移动
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0) {
                numOfZero++;
            }
            while (numOfZero > K) { //如果当前0的个数大于K，则移动左边界至当前窗口第一个0下标的下一位
                if (A[left] == 0) {
                    numOfZero--;
                }
                left++;
            }
            curMax = Math.max(curMax, i - left + 1); //更新当前最大连续个数
        }
        return curMax;
    }
}
