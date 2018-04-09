package com.netty.learn.netty.decoder;
/**
 * 编码/解码
 * netty ----> io.netty.handler.codec;
 *
 * 基本TCP处理半包解码器
 * 1, 消息头标识 : LengthFieldBasedFrameDecoder + LengthFieldPrepender
 * 2, 换行符：LineBasedFrameDecoder + StringDecoder
 * 3，自定义符： DelimiterBasedFrameDecoder + StringDecoder
 * 4，消息定长： FixedLengthFrameDecoder + StringDecoder


    MessagePack 编码/解码

    ProtoBuf 编码/解码


 **/
