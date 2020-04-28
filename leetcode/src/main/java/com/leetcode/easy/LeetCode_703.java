package com.leetcode.easy;

import java.util.PriorityQueue;

/**
 * Description 数据流中的第K大元素 https://leetcode-cn.com/problems/kth-largest-element-in-a-stream/
 * Author wanghengwei
 * Date   2020/4/28
 */
public class LeetCode_703 {

    private int size;
    private PriorityQueue<Integer> q;

    public LeetCode_703(int k, int[] nums) {
        size = k;
        q = new PriorityQueue<>(k);
        for (int num : nums) {
            add(num);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, -1};
        LeetCode_703 leetCode_703 = new LeetCode_703(3, array);
        int result = leetCode_703.add(2);
        System.out.println(result);
    }

    public int add(int val) {
        if (q.size() < size) {
            q.add(val);
        } else if (q.peek() < val) {
            q.poll();
            q.add(val);
        }
        return q.peek();
    }
}
