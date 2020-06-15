package TK2020.M05.D12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description: 三数之和 https://leetcode-cn.com/problems/3sum/
 * @Author: huangsan
 * @Date: 2020/5/12 10:32 上午
 *
 * 解题思路：求三数和为零，先排序，如果第一位大于0了，后续一定都是大于0的返结果，
 * 便利数组，后两个数一个从头到尾，一个从后往前，如果三个数相加等于0，记一次，大于0，
 * 末位数往前走一步，如果小于0，中间数往后走一步，直到俩数相等，跳出循环，继续下一个。
 */
class SSZH {

    public static void main(String[] args) {

        int[] nums = {-1, 0, 1, 2, -1, -4};
//        int[] nums = {0, 0, 0, 0, 0};
        List<List<Integer>> lists = threeSum(nums);

        System.out.println(lists);

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> finalList = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                return finalList;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {//去重复
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = -(nums[j] + nums[k]);
                if (nums[i] == sum) {
                    finalList.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (j < k && nums[j] == nums[j + 1]) {//去重复
                        j++;
                    }
                    while (j < k && nums[k] == nums[k - 1]) {//去重复
                        k--;
                    }
                }
                if (nums[i] > sum) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return finalList;
    }
}
