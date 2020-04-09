package com.game.util;

import com.alibaba.fastjson.JSON;
import com.game.container.constant.AttrType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈json格式字符串工具〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
public class JsonUtils {


    /**
     * 把json格式的属性字符串转成Map
     *
     * @param attrStr
     * @return
     */
    public static Map<AttrType, Long> parseStr(String attrStr){
        Map<AttrType, Long> attrMap = new HashMap<>();

        Map mapTypes = JSON.parseObject(attrStr);
        Iterator iterator = mapTypes.entrySet().iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            Map.Entry entry = (Map.Entry)obj;
            String str = (String) entry.getKey();
            AttrType attr = AttrType.getAttr(str);
            attrMap.put(attr, new Long((Integer) entry.getValue()));
        }
        return attrMap;
    }

}