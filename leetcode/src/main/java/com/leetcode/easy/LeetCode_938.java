package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 *  二叉搜索树的范围和 https://leetcode-cn.com/problems/range-sum-of-bst/
 */
public class LeetCode_938 {

    int count;

    public static void main(String[] args) {

    }

    public int rangeSumBST(TreeNode root, int L, int R) {
        transfer(root, L, R);
        return count;
    }

    public void transfer(TreeNode node, int L, int R) {
        if (node == null) {
            return;
        }
        transfer(node.left, L, R);
        if (node.val >= L && node.val <= R) {
            count += node.val;
        }
        transfer(node.right, L, R);
    }

}
