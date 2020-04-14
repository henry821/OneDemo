package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * Description 二叉搜索树结点最小距离 https://leetcode-cn.com/problems/minimum-distance-between-bst-nodes/
 * Author wanghengwei
 * Date   2020/4/13
 */
public class LeetCode_783 {

    private TreeNode prev;
    private int result = Integer.MAX_VALUE;

    public static void main(String[] args) {
        TreeNode normalTree = TreeUtil.createNormalTree();

        LeetCode_783 leetCode_783 = new LeetCode_783();
        leetCode_783.minDiffInBST(normalTree);
    }

    public int minDiffInBST(TreeNode root) {
        midTraverse(root);
        return result;
    }

    /**
     * 中序遍历
     *
     * @param root 父节点
     */
    private void midTraverse(TreeNode root) {
        if (root == null) {
            return;
        }
        midTraverse(root.left);
        if (prev != null) {
            result = Math.min(result, root.val - prev.val);
        }
        prev = root;
        midTraverse(root.right);
    }

}
