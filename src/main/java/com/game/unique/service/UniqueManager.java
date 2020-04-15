package com.game.unique.service;

import com.game.unique.constant.UniqueType;
import com.game.unique.entity.UniqueEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性管理器〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class UniqueManager {

    /** Map< 唯一性ID， 当前已被使用的最高值 ></> */
    private Map<Integer, Integer> uniqueMap = new HashMap<>();

    /**
     * 缓存唯一值
     *
     */
    public void cacheUnique(UniqueEntity entity){
        uniqueMap.put(entity.getId(), entity.getCurrentValue());
    }

    /**
     * 获取该类型当前已被使用的最高值
     *
     * @param type
     * @return
     */
    public int getCurrentValue(UniqueType type){
        return uniqueMap.getOrDefault(type.getId(), 0);
    }
}