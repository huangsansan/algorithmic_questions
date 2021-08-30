package com.hhb.netty.rpc;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @description:
 * @Author: huangsan
 * @Date: 2021/8/30 下午8:03
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //魔数 5+1+1+1+4+4=16 尽量满足2的n次幂
        out.writeBytes("HHHNB".getBytes());
        //版本
        out.writeByte(1);
        //序列化算法 0：jdk 1:json
        out.writeByte(0);
        //字节指令类型 业务相关的可以从msg中取，这里设置为默认
        out.writeByte(0);
        //请求序号
        out.writeInt(msg.getSequenceId());

        //json
        byte[] bytes = JSONObject.toJSONBytes(msg);

        //jdk
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
//        stream.writeObject(msg);
//        byte[] bytes = outputStream.toByteArray();

        //正文长度
        out.writeInt(bytes.length);
        //正文内容
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf magicNum = in.readBytes(5);
        String magic = magicNum.toString(Charset.defaultCharset());
        byte version = in.readByte();
        byte serializableType = in.readByte();
        byte type = in.readByte();
        int sequenceId = in.readInt();
        int length = in.readInt();
        //存储内容
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        if (serializableType == 0) {
            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message message = (Message) stream.readObject();
            log.info("{} , {} , {} , {} , {} , {}", magic, version, serializableType, type, sequenceId, length);
            log.info("jdk message: {}", message);
            out.add(message);
        } else if (serializableType == 1) {
            //json
            log.info("{} , {} , {} , {} , {} , {}", magic, version, serializableType, type, sequenceId, length);
            Message message = JSONObject.parseObject(new String(bytes, Charset.defaultCharset()), Message.class);
            log.info("json message: {}", message);
        }
    }
}
