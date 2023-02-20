package com.leetcode.normal;

/**
 * Description 只有两个键的键盘 https://leetcode.cn/problems/2-keys-keyboard/
 * Author henry
 * Date   2023/2/7
 */
public class LC_650 {

    public static void main(String[] args) {
        System.out.println(minSteps(7));
    }

    public static int minSteps(int n) {
        return minStepsNormal(1, n, 0, 0);
    }

    /**
     * 总共有[sum]个A
     * 当前粘贴板有[clipboard]个A
     * 返回[0..index]范围内的拼成A的最小操作次数
     * [steps]为已用操作数
     **/
    public static int minStepsNormal(int index, int sum, int clipboard, int steps) {
        if (index > sum) {
            return Integer.MAX_VALUE;
        }
        if (steps == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (index == sum) {
            return steps;
        }
        //如果粘贴板没有内容，则必须要先复制再粘贴
        if (clipboard == 0) {
            int nextIndex = index * 2;
            return minStepsNormal(nextIndex, sum, index, steps + 2);
        }
        //只粘贴
        int value1 = minStepsNormal(index + clipboard, sum, clipboard, steps + 1);
        //先复制再粘贴
        int nextIndex = index * 2;
        int value2 = minStepsNormal(nextIndex, sum, index, steps + 2);
        return Math.min(value1, value2);
    }

    private static String num2Char(int num) {
        char[] result = new char[num];
        for (int i = 0; i < num; i++) {
            result[i] = 'A';
        }
        return String.valueOf(result);
    }
}
