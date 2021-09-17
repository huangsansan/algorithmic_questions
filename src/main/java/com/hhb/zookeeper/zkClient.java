package com.hhb.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/9/13 下午7:30
 */
public class zkClient {

    private static String zkUrl = "172.17.90.4:2181,172.17.90.4:2182,172.17.90.4:2183/hhb";
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {

        zk = new ZooKeeper(zkUrl, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
//                try {
//                    List<String> children = zk.getChildren("/sanguo", true);
//                    System.out.println(children);
//                    List<String> list = new ArrayList<>();
//                    for (String child : children) {
//                        byte[] data = zk.getData("/sanguo/" + child, false, null);
//                        list.add(new String(data));
//                    }
//                    System.out.println(list);
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        System.out.println(zk.create("/sanguo", "helloword".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL));

//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
