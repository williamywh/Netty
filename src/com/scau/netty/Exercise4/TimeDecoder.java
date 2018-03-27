package com.scau.netty.Exercise4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder{ // ChannelInboundHandlerd的一个实现类，处理数据的拆分问题

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 每当有新数据接收的时候，ByteToMessageDecoder 都会调用 decode() 方法来处理内部的那个累积缓冲。
        if (in.readableBytes() < 4 ){
            return;
        }
        // out.add(in.readBytes(4));
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
