package com.leetcode.utils;

/**
 * Description 堆操作工具类
 * Author wanghengwei
 * Date   2020/4/28
 */
public class HeapUtil {

    /**
     * 建小顶堆，从最后一个非叶子节点开始，依次做"下沉"调整
     *
     * @param array 待调整的堆
     */
    public static void buildHeap(int[] array) {
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
    }

    /**
     * "下沉"调整，和左右子节点比较，和较小的那个节点交换位置
     *
     * @param array       待排序数组
     * @param parentIndex 要"下沉"的父节点
     * @param length      堆的有效大小
     */
    private static void downAdjust(int[] array, int parentIndex, int length) {
        //temp保存父节点的值，用于最后的赋值
        int temp = array[parentIndex];
        //获取左孩子节点
        int childIndex = parentIndex * 2 + 1;
        while (childIndex < length) {
            //如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] < array[childIndex]) {
                childIndex++;
            }
            //如果父节点小于任何一个孩子的值，则直接跳出
            if (temp <= array[childIndex]) {
                break;
            }
            //无需真正交换，单向赋值即可
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = childIndex * 2 + 1;
        }
        array[parentIndex] = temp;
    }

    /**
     * "上浮"调整，用于在一个小顶堆中插入一个新元素后的自我调整
     *
     * @param array 待调整的堆
     */
    private static void upAdjust(int[] array) {
        int childIndex = array.length - 1;
        int parentIndex = (childIndex - 1) / 2;
        //temp保存插入的叶子节点值，用于最后的赋值
        int temp = array[childIndex];
        while (childIndex > 0 && temp < array[parentIndex]) {
            //无需真正交换，单向赋值即可
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        array[childIndex] = temp;
    }

}
