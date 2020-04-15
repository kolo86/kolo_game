package com.game.item.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import lombok.Data;

/**
 * 物品表
 *
 * @author KOLO
 */
@Data
@Resource
public class ItemResource extends AbstractResource {
    /** 物品表ID */
    private int id;
    /** 道具名称 */
    private String name;
    /** 道具类型 */
    private int type;
    /** 道具效果 */
    private String attrs;
    /** 最大叠加数量 */
    private int maxAddition;

    @Override
    public int getResourceId() {
        return id;
    }
}
