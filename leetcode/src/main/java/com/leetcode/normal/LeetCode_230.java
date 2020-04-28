package com.leetcode.normal;

import com.leetcode.beans.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 二叉搜索树中第K小的元素 https://leetcode-cn.com/problems/kth-smallest-element-in-a-bst/
 * Author wanghengwei
 * Date   2020/4/28
 */
public class LeetCode_230 {

    public static void main(String[] args) {

    }

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        inOrderSearch(root, list);
        return list.get(k - 1);
    }

    private void inOrderSearch(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inOrderSearch(node.left, list);
        list.add(node.val);
        inOrderSearch(node.right, list);
    }

}
