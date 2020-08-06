package BK2020.M08.D06;

/**
 * 产生原因：
 * 1、你的应用创建了太多线程，一个应用进程创建过多的现场，超过了系统承载极限
 * 2、你的服务器不允许创建这么多的线程，默认linux允许单个进程创建1024个线程
 */
public class BK_UnableToCreateNewNativeThread {
}
