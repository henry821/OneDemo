package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * Description 二叉树的直径 https://leetcode-cn.com/problems/diameter-of-binary-tree/
 * Author wanghengwei
 * Date   2019/9/25 10:05
 */
public class LeetCode_543 {

    private int sum;

    public static void main(String[] args) {
        LeetCode_543 method = new LeetCode_543();
        TreeNode root = TreeUtil.createNormalTree();
        TreeUtil.midSearch(root);
        int sum = method.diameterOfBinaryTree(root);
        System.out.println();
        System.out.println(sum);
    }

    private int diameterOfBinaryTree(TreeNode root) {
        sum = 1;
        depth(root);
        return sum - 1;
    }

    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int L = depth(node.left);
        int R = depth(node.right);
        sum = Math.max(L + R + 1, sum);
        return Math.max(L, R) + 1;
    }

}
