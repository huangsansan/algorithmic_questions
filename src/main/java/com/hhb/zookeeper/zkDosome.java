package com.hhb.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/13 下午7:58
 */
public class zkDosome {

    private static String zkUrl = "172.17.90.4:2181,172.17.90.4:2182,172.17.90.4:2183";

    public static void main(String[] args) throws IOException {

        ZooKeeper zk = new ZooKeeper(zkUrl, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
        //上线服务器,-e的方式创建服务端
        try {
            zk.create("/auto/c", "helloword".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
