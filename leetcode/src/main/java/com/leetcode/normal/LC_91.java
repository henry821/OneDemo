package com.leetcode.normal;

import java.util.Arrays;

/**
 * Description 解码方法 https://leetcode.cn/problems/decode-ways/
 * Author henry
 * Date   2023/2/7
 */
public class LC_91 {

    public static void main(String[] args) {
        System.out.println(numDecodings("12"));
    }

    public static int numDecodings(String s) {
        if (s.isEmpty()) return 0;
        int[] dp = new int[s.length() + 1];
        Arrays.fill(dp, -1);
        int result = decodeDp(s, dp, 0);
        System.out.println(Arrays.toString(dp));
        return result;
    }

    /**
     * 无缓存版本
     * 返回[s]字符串中[0..index]区间的解码总数
     */
    public static int decodeNormal(String s, int index) {
        if (index > s.length()) {
            return 0;
        }
        //能扫描到最后一位，说明解码成功，返回1个方法数
        if (index == s.length()) {
            return 1;
        }
        char currentChar = s.charAt(index);
        //扫描到0字符的话说明解码失败，返回0个方法数
        if (currentChar == '0') {
            return 0;
        }
        if (currentChar == '1') { //扫描到1字符的话，把解析1位数和2位数的情况累加即可
            int res = decodeNormal(s, index + 1);
            res += decodeNormal(s, index + 2);
            return res;
        }
        if (currentChar == '2') {  //26以下的字符，可以解析1位或2位
            int res = decodeNormal(s, index + 1);
            if (index + 1 < s.length()) {
                char nextChar = s.charAt(index + 1);
                if (nextChar <= '6') {
                    res += decodeNormal(s, index + 2);
                }
                return res;
            }
        }
        //其余情况只能解析1位
        return decodeNormal(s, index + 1);
    }

    /**
     * 有缓存版本
     * 返回[s]字符串中[0..index]区间的解码总数
     */
    public static int decodeDp(String s, int[] dp, int index) {
        if (index > s.length()) {
            return 0;
        }
        if (dp[index] > -1) {
            return dp[index];
        }
        //能扫描到最后一位，说明解码成功，返回1个方法数
        if (index == s.length()) {
            return 1;
        }
        char currentChar = s.charAt(index);
        //扫描到0字符的话说明解码失败，返回0个方法数
        if (currentChar == '0') {
            return 0;
        } else if (currentChar == '1') { //扫描到1字符的话，把解析1位数和2位数的情况累加即可
            int res = decodeDp(s, dp, index + 1);
            res += decodeDp(s, dp, index + 2);
            dp[index] = res;
            return res;
        } else if (currentChar == '2') {  //26以下的字符，可以解析1位或2位
            int res = decodeDp(s, dp, index + 1);
            if (index + 1 < s.length()) {
                char nextChar = s.charAt(index + 1);
                if (nextChar <= '6') {
                    res += decodeDp(s, dp, index + 2);
                }
            }
            dp[index] = res;
            return res;
        }
        //其余情况只能解析1位
        int res = decodeDp(s, dp, index + 1);
        dp[index] = res;
        return res;
    }
}
