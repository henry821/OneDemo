package com.leetcode.easy;

/**
 * Description 猜数字大小 https://leetcode.cn/problems/guess-number-higher-or-lower/
 * Author henry
 * Date   2023/3/7
 */
public class LC_374 {

    public static final int TARGET = 10;

    public static void main(String[] args) {

    }

    public static int guessNumber(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (guess(middle) <= 0) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

    /**
     * 返回结果：
     * -1：我选出的数字比你猜的数字小 pick < num
     * 1：我选出的数字比你猜的数字大 pick > num
     * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
     */
    private static int guess(int num) {
        return Integer.compare(TARGET, num);
    }

}
