package TK2020.M07.D30;

/**
 * a的b次方
 * 解题思路，用指数的二分法，每次除2，相当于每次底数*底数，如果为奇数则（（底数*底数）2分之一方）*底数。
 * 一直除2，知道指数为0。这样相当于1*后面的所有res即可
 * <p>
 * Java 代码中 int32 变量 n \in [-2147483648, 2147483647]n∈[−2147483648,2147483647] ，
 * 因此当 n = -2147483648n=−2147483648 时执行 n = -nn=−n 会因越界而赋值出错。解决方法是先将 nn 存入 long 变量 bb ，后面用 bb 操作即可。
 */
public class SZDZSCF {

    public static void main(String[] args) {
        System.out.println("result:" + myPow(5, 7));
    }

    public static double myPow(double x, int n) {
        long N = n;
        if (n == 0) {
            return 1;
        }
        double res = 1.0;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        while (N > 0) {
//            if (N % 2 == 1) {
            if ((N & 1) == 1) {
                res *= x;
            }
            x *= x;
//            N = N / 2;
            N >>= 1;
        }

        return res;
    }
}
