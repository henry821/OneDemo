package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * Description 对称二叉树 https://leetcode.cn/problems/symmetric-tree/
 * Author henry
 * Date   2023/2/10
 */
public class LC_101 {

    public static void main(String[] args) {
        TreeNode root = TreeUtil.buildTree(new int[]{1, 2, 2, 3, 4, 4, 3});
        System.out.println(isSymmetric(root));
    }

    public static boolean isSymmetric(TreeNode root) {
        return dfs(root, root);
    }

    /**
     * 递归方式思路：每个节点 左子树的值 = 右子树的值 && 右子树的值 = 左子树的值
     */
    private static boolean dfs(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null) {
            return false;
        } else if (left.val != right.val) {
            return false;
        }
        return dfs(left.left, right.right) && dfs(left.right, right.left);
    }

}
