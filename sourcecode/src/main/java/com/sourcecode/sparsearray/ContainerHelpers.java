package com.sourcecode.sparsearray;

import android.util.Log;

import com.baselibrary.utils.LogUtil;

/**
 * Description
 * Author wanghengwei
 * Date   2019/7/26 16:55
 */
public class ContainerHelpers {

    // This is Arrays.binarySearch(), but doesn't do any argument validation.
    static int binarySearch(int[] array, int size, int value) {
        LogUtil.e(" 进入二分查找方法：当前有效数据长度 = " + size + ", value = " + value);
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            LogUtil.e(" 进入while循环, lo = " + lo + ", hi = " + hi + ", mid = " + mid + ", midVal = " + midVal);

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                LogUtil.e(" 找到value, 下标 = " + mid + ", 返回上级方法");
                return mid;  // value found
            }
        }
        LogUtil.e(" 未找到value, lo = " + lo + ", ~lo = " + ~lo + ", 返回上级方法");
        return ~lo;  // value not present
    }

    static int binarySearch(long[] array, int size, long value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final long midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }

}
