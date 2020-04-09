package com.game.monster.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.monster.resource.MonsterResource;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈怪物管理器〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Component
public class MonsterManager {

    /**
     * 根据怪物ID获取怪物表配置表
     *
     * @param key
     * @return
     */
    public MonsterResource getMonsterResource(int key){
        return (MonsterResource) ResourceCacheHandler.getResource(MonsterResource.class, key);
    }

}