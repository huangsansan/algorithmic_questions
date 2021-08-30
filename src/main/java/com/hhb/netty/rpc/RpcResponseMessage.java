package com.hhb.netty.rpc;

import lombok.Data;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午7:48
 */
@Data
public class RpcResponseMessage extends Message {

    //正常返回值
    private Object returnValue;

    //异常返回值
    private Exception exceptionValue;

    private int messageType = 102;

}
