package com.game.item.model;

import com.frame.resource.constant.ResourceSeparator;
import com.game.account.entity.PlayerEntity;
import com.game.item.AbstractItem;
import com.game.item.constant.ItemType;
import com.game.item.constant.RecoverType;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import lombok.Data;

/**
 * 药水类型的道具
 *
 * @author KOLO
 */
@Data
public class MedicineItem extends AbstractItem {

    @Override
    public ItemType getItemType() {
        return ItemType.MEDICINE;
    }

    public MedicineItem(){}

    public MedicineItem(int itemId, int num) {
        super(itemId, num);
    }

    /**
     * 创建药水道具
     *
     * @param itemId
     * @param num
     * @return
     */
    public static MedicineItem valueOf(int itemId, int num) {
        MedicineItem item = new MedicineItem(itemId, num);
        return item;
    }

    @Override
    public boolean equals(Object obj){
        if( !(obj instanceof MedicineItem) ){
            return false;
        }
        MedicineItem otherItem = (MedicineItem)obj;
        if(otherItem.getItemId() == this.getItemId()){
            return true;
        }
        return false;
    }

    /**
     * 使用道具
     *
     */
    @Override
    public void useItem(PlayerEntity player) {
        ItemResource itemResource = ItemManager.getResource(this.getItemId());
        String[] args = itemResource.getAttrs().split(ResourceSeparator.SHUANG_YIN_HAO);
        RecoverType recoverType = RecoverType.getType(args[0]);
        recoverType.useItem(player, args);
    }

}
