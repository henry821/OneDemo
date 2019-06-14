package com.leetcode.easy;

import com.leetcode.beans.ListNode;

/**
 * 删除链表中的节点 https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
 */
public class DeleteNodeInALinkedList {

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        ListNode node1 = new ListNode(5);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(9);

        DeleteNodeInALinkedList method = new DeleteNodeInALinkedList();
        method.addNode(head, node1);
        method.addNode(head, node2);
        method.addNode(head, node3);

        method.deleteNode(head);

        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    private void addNode(ListNode head, ListNode newNode) {
        ListNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    private void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

}
