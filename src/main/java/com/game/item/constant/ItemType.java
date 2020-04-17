package com.game.item.constant;

import com.game.item.AbstractItem;
import com.game.item.model.Equipment;
import com.game.item.model.MedicineItem;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            public AbstractItem createItem(int itemId, int num) {
                return Equipment.valueOf(itemId, num);
            }
        },
        /** 药水 */
        MEDICINE(102, "药水"){

            @Override
            public AbstractItem createItem(int itemId, int num) {
                return MedicineItem.valueOf(itemId, num);
            }
        }

    ;
    /** 道具类型ID */
    private int id;
    /** 道具类型名称 */
    private String name;
    /** Map< 道具类型ID， 道具类型 ></> */
    private static final Map<Integer, ItemType> ITEM_TYPE_MAP = new HashMap<>();

    static{
        for(ItemType type : values()){
            ITEM_TYPE_MAP.put(type.getId(), type);
        }
    }

    /**
     * 根据类型ID获取对应的类型
     *
     */
    public static ItemType getType(int typeId){
        return  ITEM_TYPE_MAP.get(typeId);
    }

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
     * 创建道具
     *
     */
    public abstract AbstractItem createItem(int itemId, int num);

    /**
     * 根据道具的最大叠加数，计算应该创建多少个道具，同时把道具创建出来
     *
     */
    public List<AbstractItem> calAndCreateItem(int itemId, int num){
        List<AbstractItem> list = new ArrayList<>();
        ItemResource itemResource = ItemManager.getResource(itemId);
        int maxAddition = itemResource.getMaxAddition();

        // 满叠加数的道具数量
        int itemNum = num / maxAddition;
        if(itemNum > 0){
            for(int i = 0 ; i < itemNum ; i++ ){
                list.add(createItem(itemId, maxAddition));
            }
        }

        // 未满叠加数的单个数量
        int lessNum = num % maxAddition;
        if(lessNum > 0 ){
            list.add(createItem(itemId, lessNum));
        }
        return list;
    }
}