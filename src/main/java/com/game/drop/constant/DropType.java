package com.game.drop.constant;

import com.game.account.entity.PlayerEntity;
import com.game.drop.handler.AbstractDropHandler;
import com.game.drop.handler.impl.RandomDropHandler;
import com.game.drop.resource.DropResource;
import com.game.item.AbstractItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落类型〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public enum DropType {
        /** 随机掉落选择器 */
        RANDOM("RANDOM", new RandomDropHandler()),

    ;

    private String name;

    private AbstractDropHandler dropHandler;
    /** Map< 类型名称， 类型 ></> */
    private static final Map<String, DropType> dropTypeMap = new HashMap<>();

    static{
        for(DropType type : values()){
            dropTypeMap.put(type.getName(), type);
        }
    }

    /**
     * 获取掉落类型
     *
     */
    public static DropType getDropType(String name){
        return dropTypeMap.get(name);
    }

    DropType(String name, AbstractDropHandler handler){
        this.name = name;
        this.dropHandler = handler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractDropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(AbstractDropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    /**
     * 命中道具
     *
     */
    public List<AbstractItem> hitItem(PlayerEntity player, DropResource dropResource){
        return getDropHandler().hitItem(player, dropResource);
    };
}