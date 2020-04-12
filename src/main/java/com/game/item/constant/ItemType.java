package com.game.item.constant;

import com.game.item.AbstractItem;
import com.game.item.model.MedicineItem;

/**
 * 〈一句话功能简述〉<br>
 * 〈道具类型〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public enum ItemType {
        /** 装备 */
        EQUIPMENT(101, "装备"){
            @Override
            public String getItemDetail(AbstractItem item) {
                return null;
            }
        },
        /** 药水 */
        MEDICINE(102, "药水"){
            @Override
            public String getItemDetail(AbstractItem item) {
                MedicineItem medicineItem = (MedicineItem) item;
                return medicineItem.toString();
            }
        }

    ;
    /** 道具类型ID */
    private int id;
    /** 道具类型名称 */
    private String name;

    ItemType(int id, String name){
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
     * 获取物品的详细信息
     *
     * @return
     */
    public abstract String getItemDetail(AbstractItem item);
}