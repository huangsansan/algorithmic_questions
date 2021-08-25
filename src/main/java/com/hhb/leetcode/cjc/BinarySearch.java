package com.hhb.leetcode.cjc;

import javax.print.DocFlavor;

/**
 * 二分查找
 * 折半查询 每次除以二 所以时间复杂度为O(logn)
 * 二分查找只能用于数组 下标访问数据
 * 只针对有序数组
 * 数据量小不适合二分查找
 * 数据量大也不适合二分查找 比如需要2GB连续的内存空间
 *
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] num = {1,2,4,5,6,6,7,8,9,9,9,99};
        //int binary = binary(num, -1);
       // System.out.println(binary);
        //double sqrt = getSqrt(196);
        //String str = String.format("%.6f",sqrt);
        //System.out.println(Double.valueOf(str));
        int first = getFirst(num, 6);
        System.out.println(first);
    }
    private static int binary(int[] arr, int val) {
        int res = -1;
        int start = 0;
        int end = arr.length-1;
        //  循环退出条件 start <= end
        while (start <= end) {
            // 中间值的计算用位运算 (end+start)/2容易溢出
            int mid = start + (end - start)>>1 ;
            if (arr[mid] == val) {
                return mid;
            } else if(arr[mid] < val) {
                start = mid+1;
            } else if(arr[mid] > val) {
                end = mid-1;
            }
        }



        return res;

    }
    /**
     * 求一个数的平方根精确到小数点后六位
     *
     */
    private static double getSqrt(int n) {
        double start = 1;
        double end = n;


        while (start <= end) {
            double mid = start+((end-start)/2f);
            if (mid * mid <= n && (mid+0.000001) * (mid+0.000001) > n) {
                return mid;
            }
            if (mid * mid < n) {
                start = mid;
            }
            if  (mid * mid > n) {
                end = mid;
            }
        }
        return 0;
    }

    /**
     * 二分查找的变形问题
     * 通过IP定位ip所属省份
     */
    private static void position(String ip) {


    }
    /**
     * 查找第一个与给定值相等的元素
     */
    private static int getFirst(int[] arr,int n) {
        int res = -1;

        int left = 0;
        int right = arr.length-1;
        int index = -1;
        while (left<=right) {
            int mid = left + (right - left) /2;
            if (arr[mid] == n) {
                index = mid;
                break;
            }
            if (arr[mid] > n) {
                right = mid-1;
            }
            if (arr[mid] < n ) {
                left = mid + 1;
            }
        }
        while (index >= left) {
            if (arr[index] == n) {
                index--;
            }else {
                break;
            }
        }
        return index+1;
    }
}
