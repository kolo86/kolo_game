package com.netty.common;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈协议消息〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
@Data
public class ProtocolMsg {

    /** 协议号 */
    private int code;
    /** 协议真实数据 */
    private byte[] data;

    public static ProtocolMsg valueOf(int code, byte[] data){
        ProtocolMsg protocol = new ProtocolMsg();
        protocol.code = code;
        protocol.data = data;
        return protocol;
    }

}