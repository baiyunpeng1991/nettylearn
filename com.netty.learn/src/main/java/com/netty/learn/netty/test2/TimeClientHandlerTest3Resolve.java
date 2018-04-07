package com.netty.learn.netty.test2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 测试粘包：解决问题
 */
public class TimeClientHandlerTest3Resolve extends ChannelHandlerAdapter {
    private byte[] req;
    private int counter;

    public TimeClientHandlerTest3Resolve() {
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
        String body = (String)msg;
        System.out.println("the body :"+body+"; counter = "+counter++);
    }
}
