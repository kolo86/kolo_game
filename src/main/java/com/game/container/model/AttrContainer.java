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

    /** 玩家持久化到数据库的属性 */
    private Map<AttrType, Long> persistenceAttrMap = new ConcurrentHashMap<>();
    /** 玩家临时属性 ： Map< 属性类型， 属性值 ></> */
    private transient Map<AttrType, Long> tempAttrMap = new ConcurrentHashMap<>();
    /** 玩家最终属性 ： Map< 属性类型， 属性值 > */
    private  transient Map<AttrType, Long> finalAttrMap = new ConcurrentHashMap<>();

    public static AttrContainer valueOf(Map<AttrType, Long> attrMap){
        AttrContainer container = new AttrContainer();
        container.persistenceAttrMap = attrMap;
        return container;
    }

    /**
     * 获取某个属性的值
     *
     * @param type
     * @return
     */
    public long getAttrValue(AttrType type){
        return finalAttrMap.getOrDefault(type, 0L);
    }

    /**
     * 计算玩家的最终属性
     *
     */
    public void calFinalAttr(){
        Map<AttrType, Long> newFinalAttrMap = new ConcurrentHashMap<>();
        newFinalAttrMap.putAll(persistenceAttrMap);

        for( Map.Entry<AttrType, Long> entry :  tempAttrMap.entrySet() ){
            AttrType attrType = entry.getKey();
            Long attrValue = entry.getValue();

            Long oldAttrValue = newFinalAttrMap.get(attrType);
            if( Objects.isNull(oldAttrValue)){
                newFinalAttrMap.put(attrType, attrValue);
            } else {
                newFinalAttrMap.put(attrType, oldAttrValue + attrValue);
            }
        }

        this.finalAttrMap = newFinalAttrMap;
    }

    /**
     * 覆盖玩家的临时属性
     *
     */
    public void coverTempAttr(Map<AttrType, Long> newTempAttrMap){
        this.tempAttrMap = newTempAttrMap;
    }
}