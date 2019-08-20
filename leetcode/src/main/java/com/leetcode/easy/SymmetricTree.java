package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 * 对称二叉树 https://leetcode-cn.com/problems/symmetric-tree/
 */
public class SymmetricTree {

    public static void main(String[] args) {

    }

    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode node1, TreeNode node2) {
        //如果两个节点同为null，则返回true
        if (node1 == null && node2 == null) {
            return true;
        }
        //如果两个节点其中有一个为null，则返回false
        if (node1 == null || node2 == null) {
            return false;
        }
        //如果两个节点都不为null,则比较：
        // 1.当前节点的值
        // 2.左节点的左子树与右节点的右子树
        // 3.左节点的右子树与右节点的左子树
        return node1.val == node2.val
                && isMirror(node1.left, node2.right)
                && isMirror(node1.right, node2.left);

    }

}
