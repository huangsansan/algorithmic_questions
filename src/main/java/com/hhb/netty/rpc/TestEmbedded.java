package com.hhb.netty.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 用来测试编码和解码器的
 */
public class TestEmbedded {
    public static void main(String[] args) throws Exception {

        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodec(),
                new LoggingHandler(LogLevel.DEBUG));

        //encode
        Message message = new Message("huangsan", "123456");
//        channel.writeOutbound(message);


        //decode
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buffer);

        ByteBuf b1 = buffer.slice(0, 100);
        b1.retain();
        ByteBuf b2 = buffer.slice(100, buffer.readableBytes() - 100);
        channel.writeInbound(b1);//会进行一次release
        channel.writeInbound(b2);
    }
}
