package com.hhb.netty.rpc;

import com.hhb.netty.rpc_env.ServiceFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午9:09
 */
@Slf4j
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) {
        RpcResponseMessage responseMessage = new RpcResponseMessage();
        try {
            HelloService service = (HelloService) ServiceFactory.getService(Class.forName(message.getInterfaceName()));
            Method method = service.getClass().getMethod(message.getMethodName(), message.getParametersType());
            String invoke = (String) method.invoke(service, message.getParametersValues());
            log.info("结果：{}", invoke);
            responseMessage.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setExceptionValue(e);
        }
        responseMessage.setSequenceId(message.getSequenceId());
        ctx.writeAndFlush(responseMessage);
    }

//    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        RpcRequestMessage message = new RpcRequestMessage(
//                1,
//                "com.hhb.netty.rpc.HelloService",
//                "helloWord",
//                String.class,
//                new Class[]{String.class},
//                new Object[]{"黄三"}
//        );
//
//        //反射的方式调用service
//        HelloService service = (HelloService) ServiceFactory.getService(Class.forName(message.getInterfaceName()));
//        Method method = service.getClass().getMethod(message.getMethodName(), message.getParametersType());
//        String invoke = (String) method.invoke(service, message.getParametersValues());
//        System.out.println(invoke);
//    }
}
