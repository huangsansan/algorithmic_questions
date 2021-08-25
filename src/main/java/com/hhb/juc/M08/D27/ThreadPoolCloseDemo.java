package com.hhb.juc.M08.D27;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2020/8/28 8:49 下午
 */
public class ThreadPoolCloseDemo {

    public static void main(String[] args) {
        allDeviceStatusMonitor();
    }

    public static void allDeviceStatusMonitor() {
        ThreadPoolExecutor myExecutor = new ThreadPoolExecutor(
                32,
                64,
                20,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
        List<String> onlineIp = new ArrayList<>();

        List<String> ips = Arrays.asList("10.0.30.50", "10.0.30.51");
        List<Future> futureList = new ArrayList<>();
        for (String ip : ips) {
            //应用telnet
            Future<String> submit = myExecutor.submit(
                    () -> {
                        if (doTelnet(ip, "8080")) {
                            return ip;
                        }
                        return "xxx";
                    });
            futureList.add(submit);
        }

        for (Future<String> future : futureList) {
            try {
                String ip = future.get(2, TimeUnit.SECONDS);
                onlineIp.add(ip);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("超时放弃");
            }
        }

        //尝试关闭线程池，如果后续还有等待线程调用shutdownnow
        myExecutor.shutdown();
        try {
            if (!myExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                myExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            myExecutor.shutdownNow();
        }
        System.out.println("全体在线设备及系统：" + onlineIp);
    }

    private static boolean doTelnet(String telnetIp, String telnetPort) {
        if ("10.0.30.50".equals(telnetIp)) {
            return true;
        }
        while (true) {
            try {
                Thread.sleep(99999999);//模拟IO等待
            } catch (InterruptedException e) {
                System.out.println("强制中断");
            }
            return false;
        }
    }
}
