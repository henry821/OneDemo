package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * Description 相同的树 https://leetcode.cn/problems/same-tree/
 * Author henry
 * Date   2023/2/10
 */
public class LC_100 {

    public static void main(String[] args) {
        TreeNode p = TreeUtil.buildTree(new int[]{1, 2, 3});
        TreeNode q = TreeUtil.buildTree(new int[]{1, 2, 3});
        System.out.println(isSameTree(p, q));
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) { //节点为null即为相等
            return true;
        } else if (p == null || q == null) { //一个节点为null，另一个节点不为null，则肯定不相等
            return false;
        } else if (p.val != q.val) { //两个节点值不一样，则肯定不相等
            return false;
        } else {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

}
