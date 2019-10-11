package com.leetcode.normal;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * Description 删除链表的倒数第N个节点 https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 * Author wanghengwei
 * Date   2019/10/11 14:58
 */
public class LeetCode_19 {

    public static void main(String[] args) {

        LeetCode_19 method = new LeetCode_19();
        ListNode head = LinkedListUtil.createLinkedList(1);
        LinkedListUtil.printLinkedList(head);
        ListNode newHead = method.removeNthFromEnd(head, 1);
        LinkedListUtil.printLinkedList(newHead);

    }

    private ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

}
