package org.zyz.netty.client;

import cn.hutool.core.io.FileUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.zyz.netty.common.bean.FileTranseData;
import org.zyz.netty.common.bean.FileTranseHead;
import org.zyz.netty.common.bean.Msg;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 客户端事件操作类
 *
 * @author jie
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    private String tempFilePath = "E:\\temp";

    private Map<String,File> msgLinkFileMap = new ConcurrentHashMap<>();
    private Map<String,FileTranseHead> msgLinkTranseHeadMap = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //数据格式验证
        if (!(msg instanceof Msg)) {
            return;
        }
        Msg _msg = (Msg) msg;
        String msgId = _msg.getMsgId();
        switch (_msg.getCmd()) {
            //发送过来文件头
            case 1:
                FileTranseHead fileTranseHead = (FileTranseHead) _msg.getData();
                String filePath = tempFilePath + File.separator + fileTranseHead.getFileName();
                File file = new File(filePath);
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                msgLinkFileMap.put(msgId,file);
                msgLinkTranseHeadMap.put(msgId,fileTranseHead);
                break;
            //发送过来文件体
            case 2:
                FileTranseData fileTranseData = (FileTranseData) _msg.getData();
                File fileTranse = msgLinkFileMap.get(msgId);
                System.out.println("收到文件:"+fileTranse+",数据大小:"+fileTranseData.getData().length);
                FileUtil.writeBytes(fileTranseData.getData(),fileTranse,0,fileTranseData.getData().length,true);
                //判断文件是否发送完毕
                FileTranseHead head =  msgLinkTranseHeadMap.get(msgId);
                if(fileTranse.length()>=head.getFileSize()){
                    System.out.println("文件传输完毕");
                    msgLinkFileMap.remove(msgId);
                    msgLinkTranseHeadMap.remove(msgId);
                }
                break;
            case 3:
                String msgText = (String) _msg.getData();
                System.out.println("收到普通文本消息：" + msgText);
                break;

            default:
                break;
        }
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("===================" + ctx.channel().localAddress().toString() + " 断开连接===================");
        super.channelInactive(ctx);
    }

    /**
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("发现异常：\r\n" + cause.getMessage());
    }
}