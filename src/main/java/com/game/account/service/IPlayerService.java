/**
 * FileName: IPlayerService
 * Author:   坤龙
 * Date:     2020/4/2 15:03
 * Description: 玩家接口类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
     * @param playerEntity
     */
    void save(PlayerEntity playerEntity);

    /**
     * 通过channel获取玩家信息
     *
     * @param channel
     * @return
     */
    PlayerEntity getPlayer(Channel channel);

    /**
     * 根据玩家账号得到玩家信息
     *
     * @param accountId
     * @return
     */
    PlayerEntity getPlayer(String accountId);

    /**
     * 通过玩家信息得到channel
     *
     * @param playerEntity
     * @return
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
     * @param channel
     * @param player
     */
    void cacheChannelMap(Channel channel, PlayerEntity player);

    /**
     * 玩家退出游戏
     *
     * @param accountId
     */
    void signOut(String accountId);

    /**
     * 关服
     *
     */
    void closeService();
}