package com.netty.learn.netty;

import com.netty.learn.netty.test1.TimeCilentHandler;
import com.netty.learn.netty.test2.TimeClientHandlerTest2;
import com.netty.learn.netty.test2.TimeClientHandlerTest3Resolve;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeCilent {


    public static void main(String[] args) {
        TimeCilent timeCilent = new TimeCilent(8080,"127.0.0.1");
        timeCilent.connect();
        //测试粘包
        timeCilent.connect_test2();
        timeCilent.connect_test3();
    }



    private int port;
    private String host;

    public TimeCilent() {
    }

    public TimeCilent(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void connect(){
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
                            ch.pipeline().addLast(new TimeCilentHandler());
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

    public void connect_test2(){
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

    public void connect_test3(){
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
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeClientHandlerTest3Resolve());
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
