package com.game.container.model;

import com.game.container.AbstractContainer;
import com.game.skill.resource.SkillResource;
import com.game.skill.service.SkillManager;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈技能容器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
public class SkillContainer extends AbstractContainer {

    /** 当前可使用的技能ID集合 */
    private List<Integer> skillList = new ArrayList<>();

    public static SkillContainer valueOf(List<Integer> list){
        SkillContainer container = new SkillContainer();
        List<Integer> sortList = sort(list);
        container.skillList = sortList;
        return container;
    }

    /**
     * 对技能排序
     *
     * @param list
     */
    private static List<Integer> sort(List<Integer> list){
        List<SkillResource> sortResourceList = new ArrayList<>();
        for(Integer skillId : list){
            SkillResource skillResource = SkillManager.getSkillResource(skillId);
            sortResourceList.add(skillResource);
        }

        Collections.sort(sortResourceList, new Comparator<SkillResource>() {
            @Override
            public int compare(SkillResource s1, SkillResource s2) {
                return s2.getPriority() - s1.getPriority() ;
            }
        });
        
        List<Integer> sortIdList = new ArrayList<>();
        for( SkillResource resource : sortResourceList ){
            sortIdList.add(resource.getId());
        }
        
        return sortIdList;
    }

}