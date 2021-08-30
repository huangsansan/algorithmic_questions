package com.hhb.netty.rpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午7:40
 */
@Data
public class Message implements Serializable {

    private String name;
    private String password;
    public int messageType;

    public Message() {

    }

    public Message(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //存储请求和相应中的版本ID，保证一一对应
    public int sequenceId;

    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

    static {

    }
}
