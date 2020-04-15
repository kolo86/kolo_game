package com.game.equipment.constant;

import com.game.account.entity.PlayerEntity;
import com.game.common.SpringContext;
import com.game.equipment.entity.EquipmentEntity;
import com.game.packback.entity.BackPackEntity;
import com.game.persistence.service.PersistenceServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈修复装备位置>
 *
 * @author KOLO
 * @create 2020/4/15
 * @since 1.0.0
 */
public enum RepairEquipmentPosition {

    /**需要修理的装备正穿戴在身上*/
    WEARING(1, "需要修理的装备正穿戴在身上"){
        @Override
        public void save(PlayerEntity player) {
            EquipmentEntity equipmentEntity = player.getEquipmentEntity();

            PersistenceServiceImpl persistenceService = (PersistenceServiceImpl)SpringContext.getBean(PersistenceServiceImpl.class);
            persistenceService.update(equipmentEntity);
        }
    },
    /** 需要修理的装备在背包中 */
    BACKPACK(2, "需要修理的装备在背包中"){
        @Override
        public void save(PlayerEntity player) {
            BackPackEntity backPackEntity = player.getBackPackEntity();

            PersistenceServiceImpl persistenceService = (PersistenceServiceImpl)SpringContext.getBean(PersistenceServiceImpl.class);
            persistenceService.update(backPackEntity);
        }
    }
    ;

    private int id;

    private String name;

    /** Map< 位置ID， 位置类型 ></> */
    private static final Map<Integer, RepairEquipmentPosition> REPAIR_EQUIPMENT_POSITION_MAP = new HashMap<>();

    static{
        for(RepairEquipmentPosition position : values()){
            REPAIR_EQUIPMENT_POSITION_MAP.put(position.getId(), position);
        }
    }

    /**
     * 根据位置ID获取对应的位置类型
     *
     * @param id
     * @return
     */
    public static RepairEquipmentPosition getPosition(int id){
        return REPAIR_EQUIPMENT_POSITION_MAP.get(id);
    }

    RepairEquipmentPosition(int id, String name){
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

    /**
     * 维修后，保存数据库
     *
     * @param player
     */
    public abstract void save(PlayerEntity player);
}