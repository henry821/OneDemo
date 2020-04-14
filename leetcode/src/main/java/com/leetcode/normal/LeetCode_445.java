package com.leetcode.normal;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

import java.util.Stack;

/**
 * Description 两数相加 II https://leetcode-cn.com/problems/add-two-numbers-ii/
 * Author wanghengwei
 * Date   2020/4/14
 */
public class LeetCode_445 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(5);
        ListNode l2 = new ListNode(5);

        LeetCode_445 leetCode_445 = new LeetCode_445();
        ListNode head = leetCode_445.addTwoNumbers(l1, l2);
        LinkedListUtil.printLinkedList(head);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        ListNode head = l1;
        while (head != null) {
            stack1.push(head.val);
            head = head.next;
        }
        head = l2;
        while (head != null) {
            stack2.push(head.val);
            head = head.next;
        }
        int minDepth = Math.min(stack1.size(), stack2.size());
        int x = 0;//进位
        ListNode next = null;
        for (int i = 0; i < minDepth; i++) {
            int a = stack1.pop();
            int b = stack2.pop();
            int c = (a + b + x) % 10; //个位
            ListNode current = new ListNode(c);
            current.next = next;
            x = (a + b + x) / 10; //十位
            next = current;
        }
        while (!stack1.empty()) {
            int a = stack1.pop();
            int c = (a + x) % 10; //个位
            ListNode current = new ListNode(c);
            current.next = next;
            x = (a + x) / 10; //十位
            next = current;
        }
        while (!stack2.empty()) {
            int a = stack2.pop();
            int c = (a + x) % 10; //个位
            ListNode current = new ListNode(c);
            current.next = next;
            x = (a + x) / 10; //十位
            next = current;
        }
        if (x == 1) {
            ListNode current = new ListNode(1);
            current.next = next;
            next = current;
        }
        return next;
    }
}
