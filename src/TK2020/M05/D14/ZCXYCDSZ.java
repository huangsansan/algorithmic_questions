package TK2020.M05.D14;

import java.util.Arrays;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/5/14 10:44 上午
 *
 * 思路： \oplus代表异或运算
 * 1、任何数和 00 做异或运算，结果仍然是原来的数，即 a \oplus 0=aa⊕0=a。
 * 2、任何数和其自身做异或运算，结果是 00，即 a \oplus a=0a⊕a=0。
 * 3、异或运算满足交换律和结合律，即 a \oplus b \oplus a=b \oplus a \oplus a=b \oplus (a \oplus a)=b \oplus0=ba⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=b。

 */
public class ZCXYCDSZ {

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 1};
        int i = singleNumber(nums);
        System.out.println(i);
    }


    public static int singleNumber(int[] nums) {
        int single = 0;
        for (int i : nums) {
            System.out.println("十进制为：" + i + "与" + single);
            System.out.println("二进制为" + Integer.toBinaryString(i) + "与" + Integer.toBinaryString(single));
            single = single ^ i;
            System.out.println("最终：" + Integer.toBinaryString(single));
        }
        return single;

        //下面思路是先遍历在比较 时间为4ms 上面为1ms
//        Arrays.sort(nums);
//        for (int i = 0; i < nums.length - 1; i = i + 2) {
//            if (nums[i] == nums[i + 1]) {
//                continue;
//            } else {
//                return nums[i];
//            }
//        }
//        return nums[nums.length - 1];
    }
}
