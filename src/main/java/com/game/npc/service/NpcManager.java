package com.game.npc.service;

import com.frame.resource.service.IResourceService;
import com.game.npc.resource.NpcResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Npc管理器〉
 *
 * @author 坤龙
 * @create 2020/4/8
 * @since 1.0.0
 */
@Component
public class NpcManager {

    @Autowired
    private IResourceService resourceService;

    /**
     * 根据npc主键ID获取对应的配置表资源
     *
     */
    public NpcResource getNpcResource(int key){
        return (NpcResource) resourceService.getResource(NpcResource.class, key);
    }

}