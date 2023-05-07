package com.leetcode.easy;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * Description 反转链表 https://leetcode.cn/problems/reverse-linked-list/
 * Author henry
 * Date   2023/4/28
 */
public class LC_206 {

    public static void main(String[] args) {
        ListNode head = LinkedListUtil.createLinkedList(5);
        LinkedListUtil.printLinkedList(head);
        ListNode newHead = reverse(head);
        LinkedListUtil.printLinkedList(newHead);
    }

    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }

    /**
     * 递归方式
     */
    public static ListNode reverse(ListNode head) {
        System.out.println("开始反转Node：" + printNode(head));
        if (head == null || head.next == null) {
            System.out.println("返回当前Head: " + printNode(head));
            return head;
        }
        ListNode newHead = reverse(head.next);
        System.out.println("newHead: " + printNode(newHead));
        System.out.println("继续反转Node: " + printNode(head));
        head.next.next = head;
        head.next = null;
        LinkedListUtil.printLinkedList(newHead);
        return newHead;
    }

    private static String printNode(ListNode node) {
        return node == null ? "null" : String.valueOf(node.val);
    }

}
