package com.demo.leetcode.easy;

/**
 * 买卖股票的最佳时机 https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {

    }

    int maxProfit(int[] prices) {
        int minProfit = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minProfit) {
                minProfit = prices[i];
            } else if (prices[i] - minProfit > maxProfit) {
                maxProfit = prices[i] - minProfit;
            }
        }
        return maxProfit;
    }
}
