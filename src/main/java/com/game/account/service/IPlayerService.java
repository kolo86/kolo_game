package com.game.account.service;

import com.game.account.entity.PlayerEntity;
import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家接口类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface IPlayerService {

    /**
     * 保存玩家信息
     *
     */
    void save(PlayerEntity playerEntity);

    /**
     * 通过channel获取玩家信息
     *
     */
    PlayerEntity getPlayer(Channel channel);

    /**
     * 根据玩家账号得到玩家信息
     *
     */
    PlayerEntity getPlayer(String accountId);

    /**
     * 通过玩家信息得到channel
     *
     */
    Channel getChannel(PlayerEntity playerEntity);

    /**
     * 初始化玩家信息
     *
     */
    void onStart();

    /**
     * 缓存玩家通道
     *
     */
    void cacheChannelMap(Channel channel, PlayerEntity player);

    /**
     * 玩家退出游戏
     *
     */
    void signOut(String accountId);

    /**
     * 关服
     *
     */
    void closeService();
}