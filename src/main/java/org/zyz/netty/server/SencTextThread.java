package org.zyz.netty.server;

import io.netty.channel.ChannelHandlerContext;
import org.zyz.netty.common.bean.Msg;

public class SencTextThread extends Thread{

    private ChannelHandlerContext context;

    public SencTextThread(ChannelHandlerContext context){
        this.context = context;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Msg msg = new Msg();
            msg.setCmd(3);
            msg.setData("我是普通文本消息");
            context.writeAndFlush(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
