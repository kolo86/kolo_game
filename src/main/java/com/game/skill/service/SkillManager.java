package com.game.skill.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.skill.resource.SkillResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 〈一句话功能简述〉<br>
 * 〈技能管理器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Component
public class SkillManager {

    private static SkillManager instance;

    @PostConstruct
    private void init(){
        instance = this;
    }

    public static SkillManager getSkillManager(){
        return instance;
    }

    /**
     * 获取技能配置表
     *
     * @param key
     * @return
     */
    public SkillResource getSkillResource(int key){
        return (SkillResource) ResourceCacheHandler.getResource(SkillResource.class, key);
    }

}