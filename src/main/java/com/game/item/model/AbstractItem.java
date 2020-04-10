package com.game.item.model;

import com.game.item.constant.ItemType;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象道具类〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public abstract class AbstractItem {

    /** 物品唯一ID */
    private long objectOnlyId;
    /** 物品表ID */
    private long itemId;
    /** 物品类型 */
    private int type;
    /** 物品数量 */
    private int num;

    /**
     * 获取道具类型
     *
     * @return
     */
    public abstract ItemType getItemType();

}