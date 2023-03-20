package org.zyz.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.zyz.netty.common.util.SerializationUtil;

/**
 * 自定义编码器
 * @author jie
 */
public class CustomEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out)  {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

    public CustomEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }
}
