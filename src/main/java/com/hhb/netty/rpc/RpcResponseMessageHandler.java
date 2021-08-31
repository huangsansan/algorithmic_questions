package com.hhb.netty.rpc;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午9:38
 */
@Slf4j
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static Map<Integer, Promise<Object>> map = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        int sequenceId = msg.getSequenceId();
        Promise<Object> promise = map.get(sequenceId);
        if (promise != null) {
            if (msg.getReturnValue() != null) {
                promise.setSuccess(msg.getReturnValue());
            } else {
                promise.setFailure(new Exception(msg.getExceptionValue()));
            }
            map.remove(sequenceId);
            System.out.println("剩余promise数量：" + map.size());
        }
    }
}
