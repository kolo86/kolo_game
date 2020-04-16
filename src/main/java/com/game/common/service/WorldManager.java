package com.game.common.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.common.resource.I18NResource;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈I18N管理器〉
 *
 * @author KOLO
 * @create 2020/4/16
 * @since 1.0.0
 */
@Component
public class WorldManager {

    /**
     * 根据ID获取I18N配置表资源
     *
     * @param id
     * @return
     */
    public static I18NResource getI18nResource(int id){
        return (I18NResource) ResourceCacheHandler.getResource(I18NResource.class, id);
    }

}