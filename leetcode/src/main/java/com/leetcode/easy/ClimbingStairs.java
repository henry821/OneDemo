package com.leetcode.easy;

/**
 * Description 爬楼梯 https://leetcode-cn.com/problems/climbing-stairs/
 * Author wanghengwei
 * Date   2019/4/25 15:54
 */
public class ClimbingStairs {

    public static void main(String[] args) {

        ClimbingStairs stairs = new ClimbingStairs();
        int count = stairs.climbStairs(4);
        System.out.println("总共有" + count + "种方法爬楼梯");
    }

    /**
     * 爬楼梯方法
     *
     * @param n 剩余楼梯数
     * @return 当前方法数
     */
    private int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] methodCount = new int[n + 1];
        methodCount[1] = 1;
        methodCount[2] = 2;
        for (int i = 3; i < methodCount.length; i++) {
            methodCount[i] = methodCount[i - 1] + methodCount[i - 2];
        }
        return methodCount[n];
    }

}
