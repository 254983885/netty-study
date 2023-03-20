package org.zyz.netty.server;

import io.netty.channel.ChannelHandlerContext;
import org.zyz.netty.common.bean.FileTranseData;
import org.zyz.netty.common.bean.FileTranseHead;
import org.zyz.netty.common.bean.Msg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

public class SencFileThread extends Thread {
    /**
     * 文件存放地址
     */
    private String sendFilePath = "D:\\软件安装包\\eclipse-inst-jre-win64.exe";

    private ChannelHandlerContext ctx;

    public SencFileThread(ChannelHandlerContext context) {
        this.ctx = context;
    }

    @Override
    public void run() {
        try {
            String msgId = UUID.randomUUID().toString();
            File file = new File(sendFilePath);
            //发送文件传输消息头
            Msg msgHead = new Msg();
            msgHead.setCmd(1);
            msgHead.setMsgId(msgId);
            FileTranseHead head = new FileTranseHead();
            head.setFileName(file.getName());
            head.setFileSize(file.length());

            msgHead.setData(head);
            ctx.writeAndFlush(msgHead);

            Msg msg = new Msg();
            msg.setCmd(2);
            msg.setMsgId(msgId);
            //发送文件到客户端
            FileTranseData fileTranseData = new FileTranseData();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024 * 100];
            int len = 0;

            while ((len = bufferedInputStream.read(buffer)) != -1) {
                byte[] data = new byte[len];
                System.arraycopy(buffer, 0, data, 0, len);
                fileTranseData.setData(data);
                msg.setData(fileTranseData);
                Thread.sleep(1000);
                ctx.writeAndFlush(msg);
            }
        } catch (Exception e) {

        }

    }
}
