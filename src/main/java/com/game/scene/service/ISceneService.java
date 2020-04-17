package com.game.scene.service;


import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈场景接口类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface ISceneService {

    /**
     * 当玩家创建完角色后，进入地图
     *
     */
    void doCreateRole(String account);

    /**
     * 玩家登录
     *
     */
    void login(String accountId);

    /**
     * 切换地图
     *
     */
    void changeMap(Channel channel, int mapId);

    /**
     * 查看当前场景中的所有玩家状态
     *
     */
    void state(Channel channel);

    /**
     * 玩家退出游戏事件
     */
    void signOut(String accountId);

}