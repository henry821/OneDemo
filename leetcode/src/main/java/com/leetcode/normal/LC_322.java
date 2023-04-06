package com.leetcode.normal;

/**
 * Description 零钱兑换 https://leetcode.cn/problems/coin-change/
 * Author henry
 * Date   2023/4/3
 */
public class LC_322 {

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));
    }

    public static int coinChange(int[] coins, int amount) {
        int[] mem = new int[amount + 1];
        int result = findWay(coins, amount, mem);
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    /**
     * 在[coins]数组中，能组成[amount]的最小方法数，[count]数组是每个amount对应最小方法数的缓存值
     */
    public static int findWay(int[] coins, int amount, int[] count) {
        if (amount < 0) {
            return -1;
        }
        if (amount == 0) {
            return 0;
        }
        if (count[amount - 1] != 0) {
            return count[amount - 1];
        }
        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            int ways = findWay(coins, amount - coin, count);
            if (ways >= 0 && ways < result) {
                result = ways + 1;
            }
        }
        count[amount - 1] = (result == Integer.MAX_VALUE) ? -1 : result;
        return result;
    }

}
