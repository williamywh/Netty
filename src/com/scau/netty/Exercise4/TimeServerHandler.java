package com.scau.netty.Exercise4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{ // ChannelInboundHandler 提供了许多事件处理的接口方法

    @Override
    public void channelActive(final ChannelHandlerContext ctx){ // 这个方法将会在连接被建立并且准备进行通信时被调用
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){ // 出现异常情况的时候进行处理
        cause.printStackTrace();
        ctx.close();
    }

}
