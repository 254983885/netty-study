package org.zyz.netty.common.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileTranseHead implements Serializable {
    private String fileName;
    private Long fileSize;
    private String fileSavePath;
}
