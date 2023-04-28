package com.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description 组合总和 https://leetcode.cn/problems/combination-sum/description/
 * Author henry
 * Date   2023/4/19
 */
public class LC_39 {

    public static void main(String[] args) {
        int[] candidates = new int[]{2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> result = combinationSum(candidates, target);
        System.out.println("result = " + result);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> combine = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        dfs(candidates, target, combine, 0, result, "");
        return result;
    }

    /**
     * 在[candidates]数组中选出与当前[index]能组合成[target]的结果集合[result]
     * [combine]存储每次尝试的组合
     */
    public static void dfs(int[] candidates, int target, List<Integer> combine,
                           int index, List<List<Integer>> result, String prefix) {
        log(candidates, target, combine, index, result, prefix);
        if (index == candidates.length) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(combine));
            return;
        }
        //使用下一个数字做判断
        dfs(candidates, target, combine, index + 1, result, prefix + " -> ");
        //使用当前数字做判断
        if (target - candidates[index] >= 0) {
            combine.add(candidates[index]);
            dfs(candidates, target - candidates[index], combine, index, result, prefix + " -> ");
            combine.remove(combine.size() - 1);
        }
    }

    private static void log(int[] candidates, int target, List<Integer> combine,
                            int index, List<List<Integer>> result, String prefix) {
        String sb = prefix + "========================" + "\n" +
                prefix + "当前index: " + index + "\n" +
                prefix + "target: " + target + "\n" +
                prefix + "原数组: " + Arrays.toString(candidates) + "\n" +
                prefix + "combine数组: " + combine.toString() + "\n" +
                prefix + "result数组: " + result + "\n";
        System.out.println(sb);
    }

}
