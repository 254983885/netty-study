package org.zyz.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.zyz.netty.common.bean.FileTranseData;
import org.zyz.netty.common.bean.FileTranseHead;
import org.zyz.netty.common.bean.Msg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * 操作类
 *
 * @author jie
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        new SencTextThread(ctx).start();

        new SencFileThread(ctx).start();


    }

    /**
     * 通道有消息触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
    /**
     * 当连接发生异常时触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //在发生异常时主动关掉连接
        ctx.close();
        log.error("发现异常：\r\n" + cause.getMessage());
    }
}
