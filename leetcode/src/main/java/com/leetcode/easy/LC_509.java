package com.leetcode.easy;

/**
 * Description 斐波那契数 https://leetcode.cn/problems/fibonacci-number/
 * Author henry
 * Date   2023/2/3
 */
public class LC_509 {

    public static void main(String[] args) {
        System.out.println(fib(5));
    }

    /**
     * 第[n]个数为第[n-1]和第[n-2]之和
     */
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
