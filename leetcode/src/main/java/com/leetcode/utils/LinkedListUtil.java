package com.leetcode.utils;

import com.leetcode.beans.ListNode;

import java.util.Random;

public class LinkedListUtil {

    public static ListNode createLinkedList(int listCount) {
        ListNode head = new ListNode(new Random().nextInt(100));
        ListNode curNode = head;
        int count = 1;
        while (count < listCount) {
            ListNode tempNode = new ListNode(new Random().nextInt(100));
            curNode.next = tempNode;
            curNode = tempNode;
            count++;
        }
        return head;
    }

    public static void printLinkedList(ListNode head) {
        ListNode curNode = head;
        StringBuilder builder = new StringBuilder();
        while (curNode != null) {
            builder.append(curNode.val).append(",");
            curNode = curNode.next;
        }
        System.out.println(builder.toString());
    }

}
