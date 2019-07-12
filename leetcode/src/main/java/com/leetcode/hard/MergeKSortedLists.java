package com.leetcode.hard;

import com.leetcode.beans.ListNode;

/**
 * 合并K个排序链表 https://leetcode-cn.com/problems/merge-k-sorted-lists/
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode result = null;
        if (lists == null) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        for (int i = 1; i < lists.length; i++) {
            result = mergeList(lists[i - 1], lists[i]);
            lists[i] = result;
        }
        return result;
    }

    private ListNode mergeList(ListNode l1, ListNode l2) {
        // 类似归并排序中的合并过程
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                cur = cur.next;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next;
                l2 = l2.next;
            }
        }
        // 任一为空，直接连接另一条链表
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return dummyHead.next;
    }

}
