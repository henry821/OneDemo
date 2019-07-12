package com.leetcode.easy;

import com.leetcode.beans.ListNode;

import java.util.HashSet;

/**
 * 环形链表 https://leetcode-cn.com/problems/linked-list-cycle/
 */
public class LinkedListCycle {

    public static void main(String[] args) {

    }

    /**
     * 借助HashSet
     *
     * @param head 头结点
     * @return 是否有环
     */
    private boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }

    /**
     * 双指针（快、慢指针）
     *
     * @param head 头结点
     * @return 是否有环
     */
    private boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

}
