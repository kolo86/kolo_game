package com.game.persistence.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

/**
 * 解析工具类
 *
 * @author KOLO
 */
public class ParseUtils {

    /**
     * 转成JSON字符串
     */
    public static String convertToJson(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.WriteClassName);
    }

    /**
     * JSON字符串转成Map对象
     */
    public static <K, V> Map<K, V> parseToMap(String json,
                                              Class<K> keyType,
                                              Class<V> valueType) {
        return JSON.parseObject(json,
                new TypeReference<Map<K, V>>(keyType, valueType) {
                });
    }

}
