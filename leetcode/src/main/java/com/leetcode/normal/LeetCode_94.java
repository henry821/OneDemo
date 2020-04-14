package com.leetcode.normal;

import com.leetcode.beans.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 二叉树的中序遍历 https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 * Author wanghengwei
 * Date   2020/4/14
 */
public class LeetCode_94 {

    private List<Integer> result = new ArrayList<>();

    public static void main(String[] args) {

    }

    public List<Integer> inorderTraversal(TreeNode root) {
        method(root);
        return result;
    }

    /**
     * 递归方法
     *
     * @param root 根节点
     */
    private void method(TreeNode root) {
        if (root == null) {
            return;
        }
        method(root.left);
        result.add(root.val);
        method(root.right);
    }

}
