package com.game.account.resource;

import com.frame.resource.AbstractResource;
import com.frame.resource.anno.Resource;
import com.game.container.constant.AttrType;
import com.game.util.JsonUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈角色配置表〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Data
@Resource
public class PlayerResource extends AbstractResource {

    /** 角色类型ID */
    private int id;
    /** 可使用技能 */
    private String skills;
    private transient List<Integer> skillList= new ArrayList();

    /** 每秒恢复的蓝量 */
    private long recoverMp;
    /** 角色初始属性 */
    private String attrs;
    /** 属性Map */
    private transient Map<AttrType, Long> attrMap;

    @Override
    public int getResourceId() {
        return id;
    }

    @Override
    public void init() {
        String[] skillArr = skills.split("-");
        for(String skill : skillArr){
            skillList.add(Integer.parseInt(skill));
        }

        attrMap = JsonUtils.parseStr(this.attrs);
    }
}