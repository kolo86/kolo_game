package com.game.item.model;


import com.game.equipment.constant.EquipmentType;
import com.game.equipment.resource.EquipmentResource;
import com.game.equipment.service.EquipmentManager;
import com.game.item.AbstractItem;
import com.game.item.constant.ItemType;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
public class Equipment extends AbstractItem {

    /** 最大耐久度 */
    private int maxDurability;
    /** 当前耐久度 */
    private int currentDurability;

    @Override
    public ItemType getItemType() {
        return ItemType.EQUIPMENT;
    }

    public Equipment(){}

    Equipment(int itemId, int num){
        super(itemId, num);
    }

    /**
     * 构建装备
     *
     */
    public static Equipment valueOf(int itemId, int num){
        Equipment equipment = new Equipment(itemId, num);

        ItemResource itemResource = ItemManager.getResource(itemId);
        String attrs = itemResource.getAttrs();
        EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(attrs));
        if(equipmentResource.getEquipmentType() == EquipmentType.WEAPON.getId()){
            equipment.maxDurability = equipmentResource.getDurability();
            equipment.currentDurability = equipmentResource.getDurability();
        }
        return equipment;
    }

    @Override
    public AbstractItem copy() {
        Equipment equipment = new Equipment();
        equipment.currentDurability = this.currentDurability;
        equipment.maxDurability = this.maxDurability;
        equipment.setObjectOnlyId(this.getObjectOnlyId());
        equipment.setItemId(this.getItemId());
        equipment.setNum(this.getNum());
        equipment.setType(this.getType());
        return equipment;
    }

    /**
     * 减少武器耐久度
     *
     */
    public void reduceDurability(int wastage){
        if(wastage > this.currentDurability){
            this.currentDurability = 0;
            return ;
        }
        this.currentDurability -= wastage;
    }

    /**
     * 恢复耐久度
     *
     */
    public void recoverDurability(){
        this.currentDurability = maxDurability;
    }

    /**
     * 当前装备是否耐久度为0
     *
     */
    public boolean isDurabilityZero(){
        return this.currentDurability == 0;
    }

}