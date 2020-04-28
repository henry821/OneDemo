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
     * 按照leetcode方式创建二叉树，传入的数组是满二叉树的长度，如果没有节点则值为-1
     *
     * @param array 数组
     * @return 二叉树
     */
    public static TreeNode buildTree(int[] array) {
        if (array.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(array[0]);
        root.left = innerBuildTree(array, 1);
        root.right = innerBuildTree(array, 2);
        return root;
    }

    private static TreeNode innerBuildTree(int[] array, int index) {
        if (index >= array.length || array[index] == -1) {
            return null;
        }
        TreeNode node = new TreeNode(array[index]);
        node.left = innerBuildTree(array, index * 2 + 1);
        node.right = innerBuildTree(array, index * 2 + 2);
        return node;
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
