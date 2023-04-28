package com.leetcode.easy;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Description 用栈实现队列 https://leetcode.cn/problems/implement-queue-using-stacks/
 * Author henry
 * Date   2023/4/25
 */
public class LC_232 {

    public static void main(String[] args) {

    }

    static class MyQueue {

        private final Stack<Integer> stack1;
        private final Stack<Integer> stack2;

        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void push(int x) {
            stack1.add(x);
        }

        public int pop() {
            if (stack2.empty()) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        public int peek() {
            if (stack2.empty()) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.peek();
        }

        public boolean empty() {
            return stack1.empty() && stack2.empty();
        }
    }

}
