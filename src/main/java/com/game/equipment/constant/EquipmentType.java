package com.game.equipment.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备类型〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
public enum  EquipmentType {
        /** 武器 */
        WEAPON(1, "武器"),
        /** 头盔 */
        HELMET(2, "头盔"),
        /** 衣服 */
        CLOTHES(3, "衣服"),
        /** 鞋子 */
        SHOES(4, "鞋子"),

    ;
    /** 装备类型 */
    private int id;
    /** 装备名称 */
    private String name;
    /** Map< 装备类型ID， 装备类型 ></> */
    private static final Map<Integer, EquipmentType> ID_EQUIPMENT_TYPE_MAP = new HashMap<>();

    static{
        for(EquipmentType type : values()){
            ID_EQUIPMENT_TYPE_MAP.put(type.getId(), type);
        }
    }

    /**
     * 根据装备类型ID获取对应的装备类型
     *
     */
    public static EquipmentType getEquipmentType(int id){
        return ID_EQUIPMENT_TYPE_MAP.get(id);
    }

    EquipmentType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}