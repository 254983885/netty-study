package org.zyz.netty.common.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class Msg implements Serializable {
    private int cmd;
    private String msgId;
    private Object data;
}
