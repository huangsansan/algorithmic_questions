package com.hhb.netty.rpc_env;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来模拟service
 */
public class ServiceFactory {

    static Properties properties;
    static Map<Class<?>, Object> map = new ConcurrentHashMap<>();

    static {
        try {
            Class<?> interfaceClass = Class.forName("com.hhb.netty.rpc.HelloService");
            Class<?> instanceClass = Class.forName("com.hhb.netty.rpc.HelloServiceImpl");
            map.put(interfaceClass, instanceClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getService(Class<T> interfaceClass) {
        return (T) map.get(interfaceClass);
    }
}
