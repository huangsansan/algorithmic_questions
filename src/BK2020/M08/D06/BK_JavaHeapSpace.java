package BK2020.M08.D06;

/**
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 */
public class BK_JavaHeapSpace {

    public static void main(String[] args) {
        Byte[] bytes = new Byte[6 * 1024 * 1024];
    }

}
