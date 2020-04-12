package com.game.item;

import com.game.item.constant.ItemType;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象道具类〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
@Data
public abstract class AbstractItem {

    /** 物品唯一ID */
    private int objectOnlyId;
    /** 物品表ID */
    private int itemId;
    /** 物品类型 */
    private int type;
    /** 物品数量 */
    private int num;

    public AbstractItem(long itemId, int num){
        // TODO 这里需要保证物品ID唯一
        this.objectOnlyId = 999;
        this.itemId = (int) itemId;
        this.num = num;
        this.type = getItemType().getId();
    }

    /**
     * 获取道具类型
     *
     * @return
     */
    public abstract ItemType getItemType();

}