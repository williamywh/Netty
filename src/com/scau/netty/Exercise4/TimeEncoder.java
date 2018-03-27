package com.scau.netty.Exercise4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        UnixTime m = (UnixTime) msg;
        ByteBuf encoder = ctx.alloc().buffer(4);
        encoder.writeInt((int) m.value());
        ctx.write(encoder,promise);
        // 当编码后的数据被写到了通道上 Netty 可以通过这个对象标记是成功还是失败。第二， 我们不需要调用 cxt.flush()。
        // 因为处理器已经单独分离出了一个方法 void flush(ChannelHandlerContext cxt),
        // 如果像自己实现 flush() 方法内容可以自行覆盖这个方法。
    }
    /*
    进一步简化操作，可以使用MessageToByteEncoder
    public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
        out.writeInt((int)msg.value());
    }
}
     */
}
