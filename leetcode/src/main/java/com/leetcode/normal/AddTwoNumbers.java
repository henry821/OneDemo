package com.leetcode.normal;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * 两数相加 https://leetcode-cn.com/problems/add-two-numbers/
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        ListNode l1 = LinkedListUtil.createLinkedList(3);
        ListNode l2 = LinkedListUtil.createLinkedList(3);
        LinkedListUtil.printLinkedList(l1);
        LinkedListUtil.printLinkedList(l2);

        AddTwoNumbers method = new AddTwoNumbers();
        ListNode result = method.addTwoNumbers(l1, l2);
        LinkedListUtil.printLinkedList(result);

    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
