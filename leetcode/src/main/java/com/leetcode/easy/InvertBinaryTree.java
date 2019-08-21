package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 * 翻转二叉树 https://leetcode-cn.com/problems/invert-binary-tree/
 */
public class InvertBinaryTree {

    public static void main(String[] args) {

    }

    private TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
        return root;
    }

}
