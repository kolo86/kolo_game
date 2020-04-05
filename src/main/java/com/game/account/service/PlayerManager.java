/**
 * FileName: PlayerManager
 * Author:   坤龙
 * Date:     2020/4/2 14:11
 * Description: 玩家管理类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.account.service;

import com.game.account.entity.PlayerEntity;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家管理类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class PlayerManager {

    private static final Logger logger = LoggerFactory.getLogger(PlayerManager.class);

    // Map< 玩家channel， 玩家对象信息 >
    private static final Map<Channel, PlayerEntity> channelPlayerMap = new ConcurrentHashMap<Channel, PlayerEntity>();
    // Map< 玩家账号ID， 玩家对象信息 >
    private static final Map<String, PlayerEntity> accountPlayerMap = new ConcurrentHashMap<String, PlayerEntity>();

    /**
     * 缓存玩家信息
     *
     * @param player
     */
    public void cachePlayerInfo(PlayerEntity player){
        channelPlayerMap.put(player.getChannel(), player);
        accountPlayerMap.put(player.getAccountId(), player);
    }

    /**
     * 缓存玩家信息到accountPlayerMap中
     *
     * @param player
     */
    public void cachePlayerMap(PlayerEntity player){
        accountPlayerMap.put(player.getAccountId(), player);
    }

    /**
     * 缓存玩家通道到channelPlayerMap中
     *
     * @param player
     */
    public void cacheChannelMap(Channel channel, PlayerEntity player){
        channelPlayerMap.put(channel, player);
    }

    /**
     * 通过玩家信息获取玩家的通道信息
     *
     * @param channel
     * @return
     */
    public PlayerEntity getPlayerByChannel(Channel channel){
        return channelPlayerMap.get(channel);
    }

    /**
     * 根据玩家账号得到玩家信息
     *
     * @param accountId
     * @return
     */
    public PlayerEntity getPlayerByAccountId(String accountId){
        return accountPlayerMap.get(accountId);
    }

    /**
     * 通过玩家信息得到channel
     *
     * @param player
     * @return
     */
    public Channel getChannelByPlayer(PlayerEntity player){
        Channel channel = null;

        Set<Map.Entry<Channel, PlayerEntity>> entrySet = channelPlayerMap.entrySet();
        for(Map.Entry<Channel, PlayerEntity> entry : entrySet){
            if(player.getAccountId().equals(entry.getValue().getAccountId())){
                channel = entry.getKey();
            }
        }

        if(channel == null ){
            logger.error("玩家{}无法获取对应的channel", player.getAccountId());
        }
        return channel;
    }

}