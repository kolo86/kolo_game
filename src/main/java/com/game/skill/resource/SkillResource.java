package com.game.skill.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import com.game.container.constant.AttrType;
import com.game.util.JsonUtils;
import lombok.Data;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈技能配置表〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
@Resource
public class SkillResource extends AbstractResource {
    /** 技能ID */
    private int id;
    /** 技能名称 */
    private String name;
    /** 技能描述 */
    private String desc;
    /** 技能伤害比例（万分值）*/
    private String skillRatio;
    private transient Map<AttrType, Long> attrMap;

    /** 技能冷却时间（毫秒） */
    private long cd;
    /** 消耗MP量 */
    private long consumeMp;
    /** 优先级。数值越高，优先级越高 */
    private int priority;

    @Override
    public int getResourceId() {
        return id;
    }

    @Override
    public void init() {
        attrMap = JsonUtils.parseStr(this.skillRatio);
    }
}