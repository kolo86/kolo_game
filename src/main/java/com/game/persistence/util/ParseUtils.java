package com.game.persistence.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

public class ParseUtils {

    public static <K, V> Map<K, V> parseToMap(String json,
                                              Class<K> keyType,
                                              Class<V> valueType) {
        return JSON.parseObject(json,
                new TypeReference<Map<K, V>>(keyType, valueType) {
                });
    }

}
