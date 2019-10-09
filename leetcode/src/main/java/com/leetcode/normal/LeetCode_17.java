package com.leetcode.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 电话号码的字母组合 https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * Author wanghengwei
 * Date   2019/10/9 10:46
 */
public class LeetCode_17 {

    private List<String> result = new ArrayList<>();

    public static void main(String[] args) {
        LeetCode_17 method = new LeetCode_17();
        List<String> strings = method.letterCombinations("23");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    private List<String> letterCombinations(String digits) {
        Map<String, String> phone = new HashMap<>();
        phone.put("2", "abc");
        phone.put("3", "def");
        phone.put("4", "ghi");
        phone.put("5", "jkl");
        phone.put("6", "mno");
        phone.put("7", "pqrs");
        phone.put("8", "tuv");
        phone.put("9", "wxyz");

        combineString("", digits, phone);

        return result;
    }

    /**
     * 拼接字符串
     *
     * @param currentString 当前字符串
     * @param remainString  剩余字符串
     * @param phoneMap      数字与字符对应关系
     */
    private void combineString(String currentString, String remainString, Map<String, String> phoneMap) {
        //如果剩下的字符串为空，说明字符串拼接结束，返回
        if (remainString.isEmpty()) {
            if (!currentString.isEmpty()) {
                result.add(currentString);
            }
            return;
        }
        //取出剩下字符串的第一位数字字符串
        String firstNumber = remainString.substring(0, 1);
        //得到数字对应的字母
        String letters = phoneMap.get(firstNumber);
        //字符串转成数组
        char[] chars = letters.toCharArray();
        //遍历数组得到每一个字母
        for (char aChar : chars) {
            //取临时变量保存当前字符串
            String temp = currentString;
            //临时变量加上当前取出的字符
            temp += aChar;
            //递归调用方法
            combineString(temp, remainString.substring(1), phoneMap);
        }
    }

}
