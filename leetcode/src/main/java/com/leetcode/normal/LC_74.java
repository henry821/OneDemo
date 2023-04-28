package com.leetcode.normal;

/**
 * Description 搜索二维矩阵 https://leetcode.cn/problems/search-a-2d-matrix/
 * Author henry
 * Date   2023/4/17
 */
public class LC_74 {

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println(searchMatrix(matrix, 9));
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        int row = binarySearchRow(matrix, target);
        return binarySearchCol(matrix[row], target);
    }

    private static int binarySearchRow(int[][] matrix, int target) {
        int low = 0;
        int high = matrix.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (matrix[mid][0] <= target) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    private static boolean binarySearchCol(int[] col, int target) {
        int low = 0;
        int high = col.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (col[mid] == target) {
                return true;
            } else if (col[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

}
