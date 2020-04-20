package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 * Description 左叶子之和 https://leetcode-cn.com/problems/sum-of-left-leaves/
 * Author wanghengwei
 * Date   2020/4/19
 */
public class LeetCode_404 {

    int count;

    public static void main(String[] args) {

    }

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left != null && (root.left.left == null && root.left.right == null)) {
            count += root.left.val;
        }
        sumOfLeftLeaves(root.left);
        sumOfLeftLeaves(root.right);
        return count;
    }

}
