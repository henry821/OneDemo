package com.leetcode.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Description 合并区间 https://leetcode-cn.com/problems/merge-intervals/
 * Author wanghengwei
 * Date   2020/4/16
 */
public class LeetCode_56 {

    public static void main(String[] args) {

    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        ArrayList<int[]> result = new ArrayList<>();
        int[] tmp = intervals[0];//临时数组
        for (int i = 1; i < intervals.length; i++) {
            if (tmp[1] < intervals[i][0]) {
                //如果前一个数组的右边界大于当前数组的左边界,则直接把前一个数组放入结果集
                result.add(tmp);
                tmp = intervals[i];
            } else {
                //否则更新tmp数组的右边界
                tmp[1] = Math.max(tmp[1], intervals[i][1]);
            }
        }
        result.add(tmp);
        return result.toArray(new int[0][]);
    }

}
