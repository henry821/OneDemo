package com.leetcode.normal;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Description 二叉树的层序遍历 https://leetcode.cn/problems/binary-tree-level-order-traversal/
 * Author henry
 * Date   2023/3/6
 */
public class LC_102 {

    public static void main(String[] args) {
        TreeNode treeNode = TreeUtil.buildTree(new int[]{3, 9, 20, -1, -1, 15, 7});
        List<List<Integer>> lists = levelOrder(treeNode);
        System.out.println(lists);
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> rowList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                rowList.add(poll.val);
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            result.add(rowList);
        }
        return result;
    }

}
