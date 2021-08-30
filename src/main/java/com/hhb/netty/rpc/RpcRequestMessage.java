package com.hhb.netty.rpc;

import lombok.Data;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午7:42
 */
@Data
public class RpcRequestMessage extends Message {
    private String interfaceName;
    private String methodName;
    //返回结果
    private Class<?> returnType;
    //参数类型
    private Class[] parametersType;
    //参数值
    private Object[] parametersValues;

    public RpcRequestMessage(int sequenceId, String interfaceName, String methodName, Class<?> returnType, Class[] parametersType, Object[] parametersValues) {
        super.sequenceId = sequenceId;
        super.messageType = 101;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parametersType = parametersType;
        this.parametersValues = parametersValues;
    }
}
