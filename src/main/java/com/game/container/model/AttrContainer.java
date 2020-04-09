package com.game.container.model;

import com.game.container.AbstractContainer;
import com.game.container.constant.AttrType;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈属性容器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
public class AttrContainer extends AbstractContainer {

    /** Map< 属性类型， 属性值 > */
    private Map<AttrType, Long> attrMap = new ConcurrentHashMap<>();

    public static AttrContainer valueOf(Map<AttrType, Long> attrMap){
        AttrContainer container = new AttrContainer();
        container.attrMap = attrMap;
        return container;
    }

    /**
     * 缓存属性值
     *
     * @param type
     * @param num
     */
    public void cacheAttr(AttrType type, long num){
        Long value = attrMap.get(type);
        if(Objects.isNull(value)){
            attrMap.put(type, num);
            return;
        }
        attrMap.put(type, value + num);
    }

    /**
     * 获取某个属性的值
     *
     * @param type
     * @return
     */
    public long getAttrValue(AttrType type){
        return attrMap.get(type);
    }
}