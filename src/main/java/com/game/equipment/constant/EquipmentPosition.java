package com.game.equipment.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备栏部位〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
public enum EquipmentPosition {
    /** 武器部位 */
    WEAPON(101, "武器", EquipmentType.WEAPON),
    /** 头盔部位 */
    HELMET(102, "头盔",EquipmentType.HELMET),
    /** 衣服部位 */
    CLOTHES(103, "衣服",EquipmentType.CLOTHES),
    /** 鞋子部位 */
    SHOES(104, "靴子",EquipmentType.SHOES)

    ;
    /** 装备栏部位ID */
    private int positionId;
    /** 部位名 */
    private String name;
    /** 该部位可穿戴的装备类型 */
    private EquipmentType equipmentType;
    /** Map< 装备类型， 装备部位 ></> */
    private static final Map<EquipmentType, EquipmentPosition> TYPE_EQUIPMENT_POSITION_MAP = new HashMap<>();

    static{
        for(EquipmentPosition position : values()){
            TYPE_EQUIPMENT_POSITION_MAP.put(position.getEquipmentType(), position);
        }
    }

    /**
     * 根据装备类型得到对应的装备部位
     *
     * @param type
     * @return
     */
    public static EquipmentPosition getPosition(EquipmentType type){
        return TYPE_EQUIPMENT_POSITION_MAP.get(type);
    }

    EquipmentPosition(int positionId, String name, EquipmentType equipmentType) {
        this.positionId = positionId;
        this.name = name;
        this.equipmentType = equipmentType;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}