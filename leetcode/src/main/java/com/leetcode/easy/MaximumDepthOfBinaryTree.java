package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * 二叉树的最大深度 https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 */
public class MaximumDepthOfBinaryTree {

    public static void main(String[] args) {
        TreeNode root = TreeUtil.createNormalTree();
        TreeUtil.preSearch(root);

        MaximumDepthOfBinaryTree method = new MaximumDepthOfBinaryTree();
        int maxDepth = method.maxDepth(root);
        System.out.println(maxDepth);
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftDepth = maxDepth(root.left);
            int rightDepth = maxDepth(root.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }
}
