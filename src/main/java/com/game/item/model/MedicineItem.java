package com.game.item.model;

import com.game.item.AbstractItem;
import com.game.item.constant.ItemType;
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
    public String toString() {
        ItemResource itemResource = ItemManager.getResource(this.getItemId());
        return "药水 ： " + itemResource.getName() + ", 数量 ： " + this.getNum();
    }
}
