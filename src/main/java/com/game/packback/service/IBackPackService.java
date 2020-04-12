package com.game.packback.service;

import com.game.packback.entity.BackPackEntity;
import io.netty.channel.Channel;


/**
 * 背包接口类
 *
 */
public interface IBackPackService {

    /**
     * 处理玩家创建角色
     *
     * @param accountId
     */
    void doCreateRole(String accountId);

    /**
     * 执行开服事件
     *
     */
    void openServer();

    /**
     * 获取背包数据
     *
     * @param accountId
     * @return
     */
    BackPackEntity getBackpack(String accountId);

    /**
     * 玩家查看背包数据
     *
     * @param channel
     */
    void backPack(Channel channel);
}
