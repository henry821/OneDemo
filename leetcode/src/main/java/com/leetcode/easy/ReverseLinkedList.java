package com.leetcode.easy;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * 反转链表 https://leetcode-cn.com/problems/reverse-linked-list
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        ListNode head = LinkedListUtil.createLinkedList(5);
        LinkedListUtil.printLinkedList(head);

        ReverseLinkedList method = new ReverseLinkedList();
        ListNode newHead = method.reverseList(head);
        LinkedListUtil.printLinkedList(newHead);
    }

    private ListNode reverseList(ListNode head) {
        ListNode preNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode nextNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;
        }
        return preNode;
    }

}
