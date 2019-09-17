package com.leetcode.easy;

import com.leetcode.beans.TreeNode;
import com.leetcode.utils.TreeUtil;

/**
 * Description 路径总和3 https://leetcode-cn.com/problems/path-sum-iii/
 * Author wanghengwei
 * Date   2019/9/17 10:57
 */
public class PathSum3 {

    public static void main(String[] args) {
        PathSum3 method = new PathSum3();
        TreeNode root = TreeUtil.createNormalTree();
    }

    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        return paths(root, sum)
                + pathSum(root.left, sum)
                + pathSum(root.right, sum);

    }

    private int paths(TreeNode root, int sum) {

        if (root == null) {
            return 0;
        }

        int res = 0;
        if (root.val == sum) {
            res += 1;
        }

        res += paths(root.left, sum - root.val);
        res += paths(root.right, sum - root.val);

        return res;
    }

}
