/**
 * FileName: RoleManager
 * Author:   坤龙
 * Date:     2020/4/3 11:16
 * Description: 角色管理者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
    // 是否已经被初始化
    private transient boolean init = false;

    // Map < 玩家账号ID， 角色信息 >
    private static final Map<String, RoleEntity> roleMap = new HashMap<String, RoleEntity>();

    /**
     * 缓存角色信息
     *
     * @param entity
     */
    public void cacheRole(RoleEntity entity){
        roleMap.put(entity.getAccountId(), entity);
    }

    /**
     * 通过账号ID获取角色信息
     *
     * @param accountId
     * @return
     */
    public RoleEntity getRole(String accountId){
        return roleMap.get(accountId);
    }

    /**
     * 关服
     *
     */
    public void closeService(){
        roleMap.clear();
    }
}