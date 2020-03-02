package com.leetcode.hard;

/**
 * Description 寻找两个有序数组的中位数 https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 * Author wanghengwei
 * Date   2020/3/2
 */
public class LeetCode_4 {

    public static void main(String[] args) {

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        //确保数组1比数组2长度小
        if (m > n) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0;
        int iMax = m;
        int halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && nums2[j - 1] > nums1[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && nums1[i - 1] > nums2[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft;
                if (i == 0) {//A分成的leftA(空集) 和 rightA(A的全部)  所以leftPart = leftA(空集) + leftB,故maxLeft = B[j-1]。
                    maxLeft = nums2[j - 1];
                } else if (j == 0) { //B分成的leftB(空集) 和 rightB(B的全部)  所以leftPart = leftA + leftB(空集),故maxLeft = A[i-1]。
                    maxLeft = nums1[i - 1];
                } else { //排除上述两种特殊情况，正常比较
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n) % 2 == 1) { //奇数，中位数正好是maxLeft
                    return maxLeft;
                }
                //偶数
                int minRight;
                if (i == m) {//A分成的leftA(A的全部) 和 rightA(空集)  所以rightPart = rightA(空集) + rightB,故minRight = B[j]。
                    minRight = nums2[j];
                } else if (j == n) {//B分成的leftB(B的全部) 和 rightB(空集)  所以rightPart = rightA + rightB(空集),故minRight = A[i]。
                    minRight = nums1[i];
                } else {//排除上述两种特殊情况，正常比较
                    minRight = Math.min(nums2[j], nums1[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

}
