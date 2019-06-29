package com.leetcode.easy;

/**
 * 合并两个有序数组 https://leetcode-cn.com/problems/merge-sorted-array/
 */
public class MergeSortedArray {

    public static void main(String[] args) {

        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};

        MergeSortedArray method = new MergeSortedArray();
        method.merge(nums1, 3, nums2, 3);
        for (int i1 : nums1) {
            System.out.print(i1 + ",");
        }
    }

    private void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m]; //使用临时数组保存num1的值
        System.arraycopy(nums1, 0, temp, 0, m);
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < m && j < n) {
            int num1 = temp[i];
            int num2 = nums2[j];
            if (num1 <= num2) {
                nums1[k++] = num1;
                i++;
            } else {
                nums1[k++] = num2;
                j++;
            }
        }
        while (i < m) {
            nums1[k++] = temp[i++];
        }
        while (j < n) {
            nums1[k++] = nums2[j++];
        }
    }

}
