package com.leetcode.mianshi;

import com.leetcode.beans.ListNode;
import com.leetcode.utils.LinkedListUtil;

/**
 * Description 返回倒数第 k 个节点 https://leetcode.cn/problems/kth-node-from-end-of-list-lcci/description/
 * Author henry
 * Date   2023/4/17
 */
public class MS_02_02 {

    public static void main(String[] args) {
        ListNode head = LinkedListUtil.createLinkedList(5);
        LinkedListUtil.printLinkedList(head);
        int i = kthToLast(head, 3);
        System.out.println(i);
    }

    public static int kthToLast(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow.val;
    }

}
