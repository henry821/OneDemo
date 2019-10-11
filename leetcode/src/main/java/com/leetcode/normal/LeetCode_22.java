package com.leetcode.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 括号生成 https://leetcode-cn.com/problems/generate-parentheses/
 * Author wanghengwei
 * Date   2019/10/11 16:15
 */
public class LeetCode_22 {

    public static void main(String[] args) {

        LeetCode_22 method = new LeetCode_22();
        List<String> strings = method.generateParenthesis(3);
        for (String string : strings) {
            System.out.println(string);
        }

    }

    private List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }

    private void backtrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        if (open < max) {
            backtrack(ans, cur + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrack(ans, cur + ")", open, close + 1, max);
        }
    }

}
