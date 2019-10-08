package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 * Description 合并二叉树 https://leetcode-cn.com/problems/merge-two-binary-trees/
 * Author wanghengwei
 * Date   2019/10/8 11:46
 */
public class LeetCode_617 {

    public static void main(String[] args) {

    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode result = new TreeNode(t1.val + t2.val);
        result.left = mergeTrees(t1.left, t2.left);
        result.right = mergeTrees(t1.right, t2.right);
        return result;
    }

}
