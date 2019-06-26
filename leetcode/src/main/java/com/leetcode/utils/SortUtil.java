package com.leetcode.utils;

/**
 * 排序工具类，各种排序算法
 */
public class SortUtil {

    public static void main(String[] args) {
        int[] array = new int[]{3, 99, 45, 1, 70, 45, 8, 20};
        printArray(array);
        System.out.println();
//        selectionSort(array);
        quickSort(array, 0, array.length - 1);
        printArray(array);
    }

    /**
     * 冒泡排序
     */
    private static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean hasSwap = false; //提前退出标志位，如果一轮循环没有数据交换，说明已经有序，则可以提前退出循环
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    hasSwap = true;
                }
            }
            if (!hasSwap) {
                break;
            }
        }
    }

    /**
     * 插入排序
     */
    private static void insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (array[j] > value) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = value;
        }
    }

    /**
     * 选择排序
     */
    private static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int curMinIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[curMinIndex]) {
                    curMinIndex = j;
                }
            }
            if (array[curMinIndex] < array[i]) {
                swap(array, i, curMinIndex);
            }
        }
    }

    /**
     * 快速排序
     *
     * @param array 数组
     * @param low   低位下标
     * @param high  高位下标
     */
    private static void quickSort(int[] array, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int temp = array[low]; //基准位
        while (i < j) {
            while (array[j] >= temp && i < j) {
                j--;
            }
            while (array[i] <= temp && i < j) {
                i++;
            }
            if (i < j) {
                swap(array, i, j);
            }
        }
        array[low] = array[i];
        array[i] = temp;
        quickSort(array, low, j - 1);
        quickSort(array, j + 1, high);
    }

    /**
     * 交换两个元素
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
    }

    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + ",");
        }
    }

}
