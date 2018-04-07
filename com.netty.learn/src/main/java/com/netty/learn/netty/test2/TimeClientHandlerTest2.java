package com.netty.learn.netty.test2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 测试粘包：还原问题
 */
public class TimeClientHandlerTest2 extends ChannelHandlerAdapter {
    private byte[] req;
    private int counter;

    public TimeClientHandlerTest2() {
        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("counter = [" + counter + "], cause = [" + cause.getCause() + "]");
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = null;
        for (int i = 0; i <10000 ; i++) {
            //准备buffer长度
           buf = Unpooled.buffer(req.length);
           //将数组写入buffer
           buf.writeBytes(req);
           //将buffer刷新到channel
           ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.writeBytes(bytes);

        String body = new String(bytes, "UTF-8");
        System.out.println("the body :"+body+"; counter = "+counter++);
    }
}
