package com.leetcode.easy;

import com.leetcode.beans.ListNode;

/**
 * Description 环形链表 https://leetcode.cn/problems/linked-list-cycle/
 * Author henry
 * Date   2023/3/9
 */
public class LC_141 {

    public static void main(String[] args) {

    }

    public boolean hasCycle(ListNode head) {
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
