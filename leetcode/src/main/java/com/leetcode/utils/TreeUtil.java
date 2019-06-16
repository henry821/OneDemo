package com.leetcode.utils;

import com.leetcode.beans.TreeNode;

public class TreeUtil {

    /**
     * 创建一个普通的树
     *
     * @return 根节点
     */
    public static TreeNode createNormalTree() {
        TreeNode a = new TreeNode(15, null, null);
        TreeNode b = new TreeNode(7, null, null);
        TreeNode c = new TreeNode(20, a, b);
        TreeNode d = new TreeNode(9, null, null);
        TreeNode root = new TreeNode(3, d, c);
        return root;
    }

    /**
     * 前序遍历树节点
     *
     * @param root 根节点
     */
    public static void preSearch(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + ",");
        preSearch(root.left);
        preSearch(root.right);
    }

    /**
     * 中序遍历树节点
     *
     * @param root 根节点
     */
    public static void midSearch(TreeNode root) {
        if (root == null) {
            return;
        }
        midSearch(root.left);
        System.out.print(root.val + ",");
        midSearch(root.right);
    }

}
