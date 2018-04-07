package com.netty.learn.netty;

import com.netty.learn.netty.test2.TimeClientHandlerTest2;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeCilent {

    public static void main(String[] args) {
        new TimeCilent().connect(8080,"127.0.0.1");
    }

    public void connect(int port,String host){
        // client thread group
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandlerTest2());
                        }
                    });

            //
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();

            //
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            //do nothing
        }finally {
            group.shutdownGracefully();
        }
    }
}
