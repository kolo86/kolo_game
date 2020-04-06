package com.netty.util;

import com.netty.proto.Message;

import java.lang.reflect.Field;

/**
 * 协议解析工具
 *
 * @author KOLO
 */
public class AgreementParseUtils {

    /**
     * 获取协议对象
     *
     * @param msg
     * @return
     */
    public static Object getAgreement(Message.Option msg){
        Object agreeObj = null;
        try{
            Class<? extends Message.Option> messageClass = msg.getClass();
            Field field = messageClass.getDeclaredField("optionContent_");
            field.setAccessible(true);
            agreeObj = field.get(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return agreeObj;
    }

}
