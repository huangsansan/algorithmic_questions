package BK2020.M08.D06;

/**
 * 元空间内存溢出
 * -XX:MaxMetaspaceSize=8m -XX:MetaspaceSize=8m
 */
public class BK_Metaspace {
    static class Test {
    }

    public static void main(String[] args) {
        int i = 0;
        while (true) {
        }

    }
}
