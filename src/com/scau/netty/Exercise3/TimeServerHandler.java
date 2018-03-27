package com.scau.netty.Exercise3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{ // ChannelInboundHandler 提供了许多事件处理的接口方法

    @Override
    public void channelActive(final ChannelHandlerContext ctx){ // 这个方法将会在连接被建立并且准备进行通信时被调用
        final ByteBuf time = ctx.alloc().buffer(4); // 为消息分配一个新的缓存4个Byte
        // time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        time.writeInt((int) System.currentTimeMillis());
        final ChannelFuture f = ctx.writeAndFlush(time);
        // ByteBuf 之所以没有这个方法因为有两个指针，一个对应读操作一个对应写操作。当你向 ByteBuf 里写入数据的时候写指针的索引就会增加，同时读指针的索引没有变化。读指针索引和写指针索引分别代表了消息的开始和结束。
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                ctx.close();
            }

        }); //匿名的 ChannelFutureListener 类用来在操作完成时关闭 Channel。 f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){ // 出现异常情况的时候进行处理
        cause.printStackTrace();
        ctx.close();
    }

}
