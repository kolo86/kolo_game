package com.game.account.service;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.account.entity.PlayerEntity;
import com.game.account.resource.PlayerResource;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    /** Map< 玩家channel， 玩家对象信息 > */
    private static final Map<Channel, PlayerEntity> CHANNEL_PLAYER_MAP = new ConcurrentHashMap<>();
    /** Map< 玩家账号ID， 玩家对象信息 > */
    private static final Map<String, PlayerEntity> ACCOUNT_PLAYER_MAP = new ConcurrentHashMap<>();
    /** 当前对象的实例 */
    private static PlayerManager instance;

    @PostConstruct
    public void init(){
        instance = this;
    }

    public static PlayerManager getPlayerManager(){
        return instance;
    }

    /**
     * 根据角色类型获取角色配置表
     *
     */
    public PlayerResource getPlayerResource(int roleType){
        return (PlayerResource) ResourceCacheHandler.getResource(PlayerResource.class, roleType);
    }

    /**
     * 缓存玩家信息
     *
     */
    public void cachePlayerInfo(PlayerEntity player){
        CHANNEL_PLAYER_MAP.put(player.getChannel(), player);
        ACCOUNT_PLAYER_MAP.put(player.getAccountId(), player);
    }

    /**
     * 缓存玩家信息到accountPlayerMap中
     *
     */
    public void cachePlayerMap(PlayerEntity player){
        ACCOUNT_PLAYER_MAP.put(player.getAccountId(), player);
    }

    /**
     * 缓存玩家通道到channelPlayerMap中
     *
     */
    public void cacheChannelMap(Channel channel, PlayerEntity player){
        CHANNEL_PLAYER_MAP.put(channel, player);
    }

    /**
     * 通过玩家信息获取玩家的通道信息
     *
     */
    public PlayerEntity getPlayerByChannel(Channel channel){
        return CHANNEL_PLAYER_MAP.get(channel);
    }

    /**
     * 根据玩家账号得到玩家信息
     *
     */
    public PlayerEntity getPlayerByAccountId(String accountId){
        return ACCOUNT_PLAYER_MAP.get(accountId);
    }

    /**
     * 通过玩家信息得到channel
     *
     */
    public Channel getChannelByPlayer(PlayerEntity player){
        Channel channel = null;

        Set<Map.Entry<Channel, PlayerEntity>> entrySet = CHANNEL_PLAYER_MAP.entrySet();
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

    /**
     * 根据玩家账号ID移除玩家channel信息
     *
     */
    public void removeChannel(String accountId){
        PlayerEntity playerEntity = ACCOUNT_PLAYER_MAP.get(accountId);
        if(playerEntity != null && playerEntity.getChannel() != null){
            CHANNEL_PLAYER_MAP.remove(playerEntity.getChannel());
        }
    }

    /**
     * 关服
     *
     */
    public void closeService(){
        ACCOUNT_PLAYER_MAP.clear();
        CHANNEL_PLAYER_MAP.clear();
    }
}