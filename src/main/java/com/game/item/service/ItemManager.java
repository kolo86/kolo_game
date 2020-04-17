package com.game.item.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.item.resource.ItemResource;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 物品管理器
 *
 * @author kolo
 */
@Data
@Component
public class ItemManager {

    /**
     * 根据id标识获取物品配置表
     *
     */
    public static ItemResource getResource(int id){
        return (ItemResource) ResourceCacheHandler.getResource(ItemResource.class, id);
    }

}
