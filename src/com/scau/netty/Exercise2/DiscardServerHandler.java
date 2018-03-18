package com.scau.netty.Exercise2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter{ // ChannelInboundHandler 提供了许多事件处理的接口方法

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // 每当从客户端收到新的数据时，这个方法会在收到消息时被调用，这个例子中，收到的消息的类型是 ByteBuf
        ctx.writeAndFlush(msg); // ctx能够触发很多Io事件和操作。
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){ // 出现异常情况的时候进行处理
        cause.printStackTrace();
        ctx.close();
    }

}
