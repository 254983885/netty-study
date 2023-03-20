package org.zyz.netty.common.bean;

import lombok.Data;

@Data
public class FileTranseData {
    private int start;
    private int end;
    private byte[] data;
}
