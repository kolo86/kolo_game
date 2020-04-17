package com.game.role.service;

import com.game.role.entity.RoleEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色管理者〉
 *
 * @author 坤龙
 * @create 2020/4/3
 * @since 1.0.0
 */
@Data
@Component
public class RoleManager {
    /** 是否已经被初始化 */
    private transient boolean init = false;

    /** Map < 玩家账号ID， 角色信息 > */
    private static final Map<String, RoleEntity> ROLE_MAP = new HashMap<>();

    /**
     * 缓存角色信息
     *
     */
    public void cacheRole(RoleEntity entity){
        ROLE_MAP.put(entity.getAccountId(), entity);
    }

    /**
     * 通过账号ID获取角色信息
     *
     */
    public RoleEntity getRole(String accountId){
        return ROLE_MAP.get(accountId);
    }

    /**
     * 关服
     *
     */
    public void closeService(){
        ROLE_MAP.clear();
    }
}