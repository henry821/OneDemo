package com.leetcode.utils;

import com.leetcode.beans.TreeNode;

import java.util.Stack;

public class TreeUtil {

    public static void main(String[] args) {
        TreeNode treeNode = buildTree(new int[]{1, 2, 3, 4, 5, 6});
        preOrderTraversalWithStack(treeNode);
    }

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


    /**
     * 使用栈进行二叉树非递归前序遍历
     *
     * @param root 根节点
     */
    private static void preOrderTraversalWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                stack.push(treeNode);
                System.out.println("入栈当前元素 " + treeNode.val + ",当前栈: " + stack.toString() + ",寻找左子节点");
                treeNode = treeNode.left;
            }
            System.out.println("treeNode == null");
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                System.out.println("弹出栈顶元素 " + treeNode.val + ",当前栈: " + stack.toString() + ",寻找右子节点");
                treeNode = treeNode.right;
            }
        }

    }

}
