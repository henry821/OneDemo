package com.leetcode.normal;

import com.leetcode.beans.ListNode;

/**
 * Description 环形链表 II https://leetcode.cn/problems/linked-list-cycle-ii/description/
 * Author henry
 * Date   2023/3/9
 */
public class LC_142 {

    public static void main(String[] args) {

    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            if (fast == slow) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

}
