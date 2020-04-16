package com.leetcode.easy;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * Description 合并两个有序链表 https://leetcode-cn.com/problems/merge-two-sorted-lists/
 * Author wanghengwei
 * Date   2020/4/16
 */
public class LeetCode_21 {

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        ListNode l1_1 = new ListNode(2);
        l1.next = l1_1;
        l1_1.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        ListNode l2_1 = new ListNode(3);
        l2.next = l2_1;
        l2_1.next = new ListNode(4);

        LeetCode_21 leetCode_21 = new LeetCode_21();
        ListNode listNode = leetCode_21.mergeTwoLists(l1, l2);
        LinkedListUtil.printLinkedList(listNode);

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode temp = new ListNode(0);
        ListNode x = temp;
        while (l1 != null && l2 != null) {
            int a = l1.val;
            int b = l2.val;
            if (a < b) {
                temp.next = l1;
                temp = temp.next;
                l1 = l1.next;
            } else {
                temp.next = l2;
                temp = temp.next;
                l2 = l2.next;
            }
        }
        if (l1 != null) {
            temp.next = l1;
        }
        if (l2 != null) {
            temp.next = l2;
        }
        return x.next;
    }

}
