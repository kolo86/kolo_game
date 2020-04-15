package com.game.drop.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.drop.resource.DropResource;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落管理器〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class DropManager {

    /**
     * 获取掉落表资源
     *
     * @param dropId
     * @return
     */
    public static DropResource getResource(int dropId){
        return (DropResource) ResourceCacheHandler.getResource(DropResource.class, dropId);
    }

}