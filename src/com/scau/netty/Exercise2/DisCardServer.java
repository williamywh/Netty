package com.scau.netty.Exercise2;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DisCardServer {
    private int port;
    public DisCardServer(int port){
        this.port = port;
    }
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 处理I/O操作的多线程事件循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap(); // 启动NIO服务的辅助启动类
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 类来举例说明一个新的 Channel 如何接收进来的连接
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 事件处理类
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置这里指定的 Channel 实现的配置参数
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // option() 是提供给NioServerSocketChannel 用来接收进来的连接。childOption() 是提供给由父管道 ServerChannel 接收到的连接，在这个例子中也是 NioServerSocketChannel。
            ChannelFuture f = b.bind(port).sync(); // 绑定端口，开始接受进来的链接
            // 等待服务器 socket关闭；
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 9090;
        }
        new DisCardServer(port).run();
    }
}
