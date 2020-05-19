package com.leetcode.easy;

/**
 * Description 2的幂 https://leetcode-cn.com/problems/power-of-two/
 * Author wanghengwei
 * Date   2020/5/18
 */
public class LeetCode_231 {

    public static void main(String[] args) {
        LeetCode_231 leetCode_231 = new LeetCode_231();
        System.out.println(leetCode_231.isPowerOfTwo2(0));

    }

    public boolean isPowerOfTwo(int n) {
        int temp = 1;
        while (temp <= n) {
            if (temp == n) {
                return true;
            }
            temp = temp * 2;
        }
        return false;
    }

    public boolean isPowerOfTwo2(int n) {
        return n > 0 && (n & n - 1) == 0;
    }

}
