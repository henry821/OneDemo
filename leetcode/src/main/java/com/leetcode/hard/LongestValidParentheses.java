package com.leetcode.hard;

import java.util.Stack;

/**
 * 最长有效括号 https://leetcode-cn.com/problems/longest-valid-parentheses/
 */
public class LongestValidParentheses {

    public static void main(String[] args) {

    }

    private int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int maxans = 0;
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

}
