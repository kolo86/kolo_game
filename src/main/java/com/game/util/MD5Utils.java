package com.game.util;

import org.springframework.util.DigestUtils;

/**
 *  加密工具
 *
 * @author KOLO
 */
public class MD5Utils {

    /**
     * 对字符串进行加密
     *
     * @param content
     * @return
     */
    public static String encryption(String content){
        byte[] md5Digest = DigestUtils.md5Digest(content.getBytes());
        return new String(md5Digest);
    }

}
