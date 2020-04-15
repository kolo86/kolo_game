package com.game.equipment.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import com.game.container.constant.AttrType;
import com.game.util.JsonUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备资源表〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
@Resource
public class EquipmentResource extends AbstractResource {
    /** 装备ID */
    private int id;
    /** 装备名称 */
    private String name;
    /** 装备类型 */
    private int equipmentType;
    /** 耐久度 */
    private int durability;
    /** 装备属性信息 */
    private String attrs;
    private transient Map<AttrType, Long> attrMap = new HashMap<>();

    @Override
    public int getResourceId() {
        return id;
    }

    @Override
    public void init() {
        attrMap = JsonUtils.parseStr(this.attrs);
    }
}