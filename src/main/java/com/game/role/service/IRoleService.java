package com.game.role.service;

import com.game.role.entity.RoleEntity;
import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色接口类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface IRoleService {

    /**
     * 玩家创建角色
     *
     */
    void doCreateRole(Channel channel, int roleType);

    /**
     * 玩家登录
     *
     */
    void login(String accountId);

    /**
     * 服务器起服时的操作
     *
     */
    void onStart();

    /**
     * 通过账号ID获取角色信息
     *
     */
    RoleEntity getRoleByAccountId(String accountId);

    /**
     * 关服
     *
     */
    void closeService();
}