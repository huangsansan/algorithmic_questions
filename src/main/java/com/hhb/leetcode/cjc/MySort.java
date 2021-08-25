package com.hhb.leetcode.cjc;

import org.omg.PortableInterceptor.INACTIVE;

/**
 * 冒泡、插入、选择
 * 快排、归并
 * 堆排序、桶排序、计数排序、基数排序
 */
public class MySort {
    public static void main(String[] args) {
        int[] num = {7,6,4,2,3,9,87};
        mergeSort(num,0,num.length-1);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }

    /**
     * 冒泡排序
     * 两两比较交换
     * @param arr
     */
    private static void maopoaSort(int[] arr) {
        int length = arr.length;;
        for (int i = 0; i < length; i++) {
            for (int j = 1; j < length; j++) {
                if (arr[j]<arr[j-1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = tmp;
                }
            }
        }
    }

    /**
     * 插入排序
     * 默第一个排序，从第二个开始比较放到前面的位置
     * @param arr
     */
    private static void insertSort(int[] arr) {
        int length = arr.length;

        for (int i = 0; i < length-1; i++) {
            int tmp = arr[i+1];
            int index = i;
            while ( index >= 0 && arr[index] > tmp){
                arr[index+1] = arr[index];
                arr[index] = tmp;
                index--;
            }
        }
    }

    /**
     * 选择排序
     * 查找未排序的最小值与已排序的交换
     * @param arr
     */
    private static void selectSort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int j = length-1;
            int tmp = arr[j];
            int indexj = j;
            while (j>i) {
                if (arr[j] < tmp) {
                    tmp = arr[j];
                    indexj = j;
                }
                j--;
            }
            if (arr[i] > tmp) {
                arr[indexj] = arr[i];
                arr[i] = tmp;
            }
        }
    }

    /**
     * 快速排序 找到基准然后分左右
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start > end) {
            return;
        }
        int index = start;
        int tmp = arr[start];
        int left = start;
        int right = end;

        while (left < right) {
            // 右指针往左移
            while (arr[right] > tmp && left < right) {
                right--;
            }
            //左指针往右移
            while (arr[left] <= tmp && left< right) {
                left++;
            }
            // 交换左右数据
            int t = arr[left];
            arr[left] = arr[right];
            arr[right] = t;
        }
        // 做右指针重合之后交换这个值与index的值
        arr[index] = arr[left];
        arr[left] = tmp;
        quickSort(arr, start, left-1);
        quickSort(arr, left+1, end);


    }


    /**
     * 归并排序
     */
    private static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start+end)/2;
            mergeSort(arr,start,mid);
            mergeSort(arr, mid+1, end);
            merge(arr,start,mid,end);
        }

    }
    private static void merge(int[] arr, int start,int mid, int end) {
        int[] tmp = new int[end-start+1];
        int left = start;
        int right = mid+1;
        int index = 0;

        while (left <= mid && right <= end) {
            if (arr[left] < arr[right]) {
                tmp[index++] = arr[left++];
            } else {
                tmp[index++] = arr[right++];
            }
        }
        while (left <= mid) {
            tmp[index++] = arr[left++];
        }
        while (right <= end) {
            tmp[index++] = arr[right++];
        }
        for (int i = 0; i < tmp.length; i++) {
            arr[start++] = tmp[i];
        }
    }

}
