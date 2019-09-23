package com.leetcode.easy;

import com.leetcode.beans.TreeNode;

/**
 * Description 把二叉搜索树转换为累加树 https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
 * Author wanghengwei
 * Date   2019/9/20 17:27
 */
public class LeetCode_538 {

    public static void main(String[] args) {

    }

    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }

        rightMidLeftSearch(root, 0);
        return root;

    }

    /**
     * 按照 右子树 —— 当前节点 —— 左子树顺序遍历
     *
     * @param root   当前节点
     * @param preSum 遍历到当前节点之前比当前节点大的数字总和
     * @return 所有比当前节点大的节点数字总和
     */
    private int rightMidLeftSearch(TreeNode root, int preSum) {
        int sum = preSum;
        if (root == null) {
            return sum;
        }
        sum = rightMidLeftSearch(root.right, sum);

        int val = root.val;
        root.val += sum;
        sum += val;

        sum = rightMidLeftSearch(root.left, sum);

        return sum;
    }

}
