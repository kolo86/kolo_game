package com.game.packback.service;

import com.game.packback.entity.BackPackEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 背包管理器
 *
 */
@Data
@Component
public class BackpackManager {

    /** 是否已经起服初始化 */
    private transient boolean init = false;

    /** Map < 账号ID， 背包实体 ></> */
    private Map<String , BackPackEntity> backpackMap = new HashMap<>();

    /**
     * 缓存背包实体数据
     *
     * @param entity
     */
    public void cacheBackpack(BackPackEntity entity){
        backpackMap.put(entity.getAccountId(), entity);
    }

    /**
     * 根据账号ID获取背包数据
     *
     * @param accountId
     * @return
     */
    public BackPackEntity getEntity(String accountId){
        BackPackEntity backPackEntity = backpackMap.get(accountId);
        return backPackEntity;
    }
}