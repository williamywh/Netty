package com.scau.netty.Exercise3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/*
public class TimeClient {
    public static void main(String[] args) throws Exception{
         String host = args[0];
//        String host = "localhost";
//        int port = 9999;
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap(); // 非服务器的channel
            b.group(workGroup); // 客户端指定一个EventGroup，就会作为一个bossgroup，也作为workgrou，因此不需要bossgroup
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true); //  不像在使用 ServerBootstrap 时需要用 childOption() 方法，因为客户端的 SocketChannel 没有父亲
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });
            // 启动客户端
            ChannelFuture f =  b.connect(host, port).sync(); // 用 connect() 方法代替了 bind() 方法
            // 等待连接关闭
            f.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
*/
public class TimeClient {

    public static void main(String[] args) throws Exception {

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
                }
            });

            // 启动客户端
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}