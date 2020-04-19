package com.game.equipment.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.equipment.entity.EquipmentEntity;
import com.game.equipment.resource.EquipmentResource;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈管理管理器〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
@Component
public class EquipmentManager {
    /** Map< 玩家账号ID， 装备信息 ></> */
    private Map<String, EquipmentEntity> equipmentEntityMap = new HashMap<>();

    /**
     * 缓存装备数据
     *
     */
    public void cache(EquipmentEntity equipmentEntity){
        equipmentEntityMap.put(equipmentEntity.getAccountId(), equipmentEntity);
    }

    /**
     * 根据玩家ID获取装备信息
     *
     */
    public EquipmentEntity getEntity(String accountId){
        return equipmentEntityMap.get(accountId);
    }

    /**
     * 根据配置表ID获取配置表资源
     *
     */
    public static EquipmentResource getResource(int key){
        return (EquipmentResource) ResourceCacheHandler.getResource(EquipmentResource.class, key);
    }

}