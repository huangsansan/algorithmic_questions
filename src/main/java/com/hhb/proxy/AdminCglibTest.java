package com.hhb.proxy;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/31 下午5:09
 */
public class AdminCglibTest {
    public static void main(String[] args) {
        AdminCglibService service = new AdminCglibService();
        AdminCglibServiceProxy proxy = new AdminCglibServiceProxy(service);

        AdminCglibService proxyInstance = (AdminCglibService) proxy.getProxyInstance();
        proxyInstance.select();
    }
}
