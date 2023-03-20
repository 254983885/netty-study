package org.zyz.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.zyz.netty.common.bean.Msg;
import org.zyz.netty.server.CustomDecoder;
import org.zyz.netty.server.CustomEncoder;

public class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 这个方法在Channel被注册到EventLoop的时候会被调用
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("=========连接到服务端=========");
        System.out.println("channelId："+socketChannel.id());
        //对象传输处理
        socketChannel.pipeline().addLast(new CustomDecoder(Msg.class));
        socketChannel.pipeline().addLast(new CustomEncoder(Msg.class));
        socketChannel.pipeline().addLast(new MyClientHandler());
    }
}