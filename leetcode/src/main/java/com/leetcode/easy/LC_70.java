package com.leetcode.easy;

/**
 * Description 爬楼梯 https://leetcode.cn/problems/climbing-stairs/
 * Author henry
 * Date   2023/2/3
 */
public class LC_70 {

    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }

    public static int climbStairs(int n) {
        int[] dp = new int[n + 1];
        return climbStairsDp(n, dp);
    }

    /**
     * 爬[n]级楼梯有多少种方法
     */
    public static int climbStairsDp(int n, int[] dp) {
        if (dp[n] > 0) {
            return dp[n];
        }
        //退出条件, 0级楼梯有0种方法, 1级楼梯有1种方法, 2级楼梯有2种方法
        if (n <= 2) {
            dp[n] = n;
            return n;
        }

        //如果最后爬1步，返回爬[n-1]级楼梯的方法数
        //如果最后爬2步，返回爬[n-2]级楼梯的方法数
        //最后把两种方法的总数相加
        int result = climbStairsDp(n - 1, dp) + climbStairsDp(n - 2, dp);
        dp[n] = result;
        return result;
    }
}
