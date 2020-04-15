package com.game.drop.model;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈待选择的道具〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
public class SelectItem {
    /** 概率 */
    private double prob;
    /** 道具ID */
    private int itemId;

    public static SelectItem valueOf(double prob, int itemId){
        SelectItem item = new SelectItem();
        item.itemId = itemId;
        item.prob = prob;
        return item;
    }

}