package com.game.equipment.entity;

import com.game.container.constant.AttrType;
import com.game.equipment.constant.EquipmentPosition;
import com.game.equipment.constant.EquipmentType;
import com.game.equipment.resource.EquipmentResource;
import com.game.equipment.service.EquipmentManager;
import com.game.item.AbstractItem;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.persistence.AbstractEntity;
import com.game.persistence.util.ParseUtils;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 〈一句话功能简述〉<br>
 * 〈装备〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
@Entity(name = "equipment")
@Table(appliesTo = "equipment", comment = "装备")
public class EquipmentEntity extends AbstractEntity {

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID'")
    private String accountId;

    @Column(columnDefinition = "mediumblob comment '装备信息'")
    private String equipmentMapJson;

    /** 缓存的装备信息， Map< 部位ID， 装备 ></> */
    private transient Map<Integer, AbstractItem> equipmentMap = new HashMap<>();

    /** 装备属性, Map< 属性类型， 属性值 ></> */
    private transient Map<AttrType, Long> equipmentAttrMap = new HashMap<>();

    @Override
    public void convert() {
        equipmentMapJson = ParseUtils.convertToJson(equipmentMap);
    }

    @Override
    public void parse() {
        equipmentMap = ParseUtils.parseToMap(equipmentMapJson, Integer.class, AbstractItem.class);
    }


    /**
     * 创建装备实体
     *
     */
    public static EquipmentEntity valueOf(String accountId){
        EquipmentEntity entity = new EquipmentEntity();
        entity.accountId = accountId;
        return entity;
    }

    /**
     * 穿戴装备
     *
     */
    public void wear(AbstractItem item){
        ItemResource itemResource = ItemManager.getResource(item.getItemId());
        EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(itemResource.getAttrs()));
        EquipmentType type = EquipmentType.getEquipmentType(equipmentResource.getEquipmentType());
        EquipmentPosition position = EquipmentPosition.getPosition(type);
        equipmentMap.put(position.getPositionId(), item);
    }

    /**
     * 重新计算玩家的装备属性
     *
     */
    public void reCalEquipmentAttr(){
        Map<AttrType, Long> newEquipmentAttrMap = new HashMap<>();

        for( Map.Entry<Integer, AbstractItem> entry : equipmentMap.entrySet() ){
            AbstractItem abstractItem = entry.getValue();
            ItemResource itemResource = ItemManager.getResource(abstractItem.getItemId());
            EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(itemResource.getAttrs()));
            Map<AttrType, Long> attrMap = equipmentResource.getAttrMap();

            for( Map.Entry<AttrType, Long> attrEntry :  attrMap.entrySet() ){
                Long oldAttrValue = newEquipmentAttrMap.get(attrEntry.getKey());
                if(Objects.isNull( oldAttrValue )){
                    newEquipmentAttrMap.put(attrEntry.getKey(), attrEntry.getValue());
                } else {
                    newEquipmentAttrMap.put(attrEntry.getKey(), attrEntry.getValue() + oldAttrValue );
                }
            }
        }

        this.equipmentAttrMap = newEquipmentAttrMap;
    }

    /**
     * 脱下装备
     *
     */
    public AbstractItem deequipment(int position){
        AbstractItem abstractItem = equipmentMap.get(position);
        this.equipmentMap.remove(position);
        return abstractItem;
    }

}