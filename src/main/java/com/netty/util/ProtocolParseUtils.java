package com.netty.util;

import com.netty.common.ProtocolEnum;
import com.netty.common.ProtocolMsg;

import java.lang.reflect.Method;

/**
 * 协议解析工具
 *
 * @author KOLO
 */
public class ProtocolParseUtils {

    /**
     * 获取协议对象
     *
     */
    public static Object getProtocolObj(ProtocolMsg msg){
        Object protocolObj = null;
        try{
            int code = msg.getCode();
            Class<?> protocolType = ProtocolEnum.getProtocolType(code);

            Method method = protocolType.getDeclaredMethod("parseFrom", msg.getData().getClass());
            protocolObj = method.invoke(null, msg.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return protocolObj;
    }

}
