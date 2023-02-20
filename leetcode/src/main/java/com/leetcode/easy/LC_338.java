package com.leetcode.easy;

import java.util.Arrays;

/**
 * Description 比特位计数 https://leetcode.cn/problems/counting-bits/
 * Author henry
 * Date   2023/2/3
 */
public class LC_338 {

    public static void main(String[] args) {
        int[] result = countBits(5);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 思路：把十进制数拆解成被2整除的部分和余数部分，找到整除部分对应1的个数和余数部分对应1的个数并相加
     * <p>
     * countBits[0] = 0
     * countBits[1] = 1
     * ……
     * countBits[n] = countBits[n/2]+countBits[n%2]
     * <p>
     * 十进制      二进制     整数      余数      1的个数
     * 0          0         0        0        0
     * 1          01        0        1        1
     * 2          10        1        0        1
     * 3          11        1        1        2
     * 4          100       2        0        1
     * 5          101       2        1        2
     */
    public static int[] countBits(int n) {
        int[] result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int a = i / 2;
            int b = i % 2;
            if (a == 0 && b == 0) {
                result[i] = 0;
            } else if (a == 0 && b == 1) {
                result[i] = 1;
            } else {
                result[i] = result[a] + result[b];
            }
        }
        return result;
    }

}
