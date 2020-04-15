package com.game.monster.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import com.game.container.constant.AttrType;
import com.game.util.JsonUtils;
import lombok.Data;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈怪物配置表〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
@Resource
public class MonsterResource extends AbstractResource {

    /** 怪物ID*/
    private int id;
    /** 怪物名称*/
    private String name;
    /** 属性 */
    private String attrs;
    /** 属性Map */
    private transient Map<AttrType, Long> attrMap;
    /** 掉落选择器ID */
    private int dropId;

    @Override
    public int getResourceId() {
        return id;
    }

    @Override
    public void init() {
        attrMap = JsonUtils.parseStr(this.attrs);
    }
}