package com.sourcecode.sparsearray;

import com.baselibrary.utils.LogUtil;

/**
 * Description
 * Author wanghengwei
 * Date   2019/7/26 16:57
 */
public class GrowingArrayUtils {

    /**
     * Appends an element to the end of the array, growing the array if there is no more room.
     * @param array The array to which to append the element. This must NOT be null.
     * @param currentSize The number of elements in the array. Must be less than or equal to
     *                    array.length.
     * @param element The element to append.
     * @return the array to which the element was appended. This may be different than the given
     *         array.
     */
    public static <T> T[] append(T[] array, int currentSize, T element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            @SuppressWarnings("unchecked")
//            T[] newArray = ArrayUtils.newUnpaddedArray(
//                    (Class<T>) array.getClass().getComponentType(), growSize(currentSize));
            T[] newArray = (T[]) HookArrayUtils.newUnpaddedArray(
                    (Class<T>) array.getClass().getComponentType(), growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }


    public static int[] append(int[] array, int currentSize, int element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
//            int[] newArray = ArrayUtils.newUnpaddedIntArray(growSize(currentSize));
            int[] newArray = (int[]) HookArrayUtils.newUnpaddedIntArray(growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Inserts an element into the array at the specified index, growing the array if there is no
     * more room.
     *
     * @param array The array to which to append the element. Must NOT be null.
     * @param currentSize The number of elements in the array. Must be less than or equal to
     *                    array.length.
     * @param element The element to insert.
     * @return the array to which the element was appended. This may be different than the given
     *         array.
     */
    public static <T> T[] insert(T[] array, int currentSize, int index, T element) {
        LogUtil.e("--进入插入Key方法,当前currentSize(有效数据长度) = " + currentSize + ", array.length(真实数组长度) = " + array.length + ", index(插入下标) = " + index + ", value(插入元素) = " + element);
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            LogUtil.e("--当前有效长度+1没有超过真实数组长度,则把当前数组index(" + index + ")位置及以后数据向后移动一位,Value(" + element + ")插入到index(" + index + ")位置");
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        @SuppressWarnings("unchecked")
//        T[] newArray = ArrayUtils.newUnpaddedArray((Class<T>)array.getClass().getComponentType(),
//                growSize(currentSize));
        T[] newArray = (T[]) HookArrayUtils.newUnpaddedArray((Class<T>)array.getClass().getComponentType(),
                growSize(currentSize));
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        LogUtil.e("--当前有效长度+1超过真实数组长度,则创建容量为2倍的新数组,把原数组index(" + index + ")之前的值放入新数组,在新数组index处(" + index + ")插入Value(" + element + "),原数组剩下的值插入新数组index+1之后");
        return newArray;
    }


    public static int[] insert(int[] array, int currentSize, int index, int element) {
        LogUtil.e("--进入插入Key方法,当前currentSize(有效数据长度) = " + currentSize + ", array.length(真实数组长度) = " + array.length + ", index(插入下标) = " + index + ", key(插入元素) = " + element);
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            LogUtil.e("--当前有效长度+1没有超过真实数组长度,则把当前数组index(" + index + ")位置及以后数据向后移动一位,Key(" + element + ")插入到index(" + index + ")位置");
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

//        int[] newArray = ArrayUtils.newUnpaddedIntArray(growSize(currentSize));
        int[] newArray = (int[]) HookArrayUtils.newUnpaddedIntArray(growSize(currentSize));
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        LogUtil.e("--当前有效长度+1超过真实数组长度,则创建容量为2倍的新数组,把原数组index(" + index + ")之前的值放入新数组,在新数组index处(" + index + ")插入Key(" + element + "),原数组剩下的值插入新数组index+1之后");
        return newArray;
    }

    /**
     * Given the current size of an array, returns an ideal size to which the array should grow.
     * This is typically double the given size, but should not be relied upon to do so in the
     * future.
     */
    public static int growSize(int currentSize) {
        return currentSize <= 4 ? 8 : currentSize * 2;
    }

    // Uninstantiable
    private GrowingArrayUtils() {}

}
