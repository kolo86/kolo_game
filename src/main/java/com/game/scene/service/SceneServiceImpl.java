package com.game.scene.service;

import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.common.constant.I18nId;
import com.game.persistence.service.IPersistenceService;
import com.game.scene.AbstractMapHandler;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈场景实现类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class SceneServiceImpl implements ISceneService {

    private static final Logger logger = LoggerFactory.getLogger(SceneServiceImpl.class);

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IPersistenceService persistenceService;

    /**
     * 当玩家创建完角色后，进入地图
     *
     */
    @Override
    public void doCreateRole(String account) {
        PlayerEntity player = playerService.getPlayer(account);

        AbstractMapHandler handler = SceneType.MAINCITY.getHandler();
        if(!handler.checkChangeMapCondition(player, SceneType.MAINCITY.getMapId())){
            logger.error("玩家{}创建角色后，进入地图{}失败！玩家当前地图ID为：{}", player.getAccountEntity().getNickName(), SceneType.MAINCITY.getMapName(), player.getMapId());
        }
        handler.doEnterMap(player);
        persistenceService.update(player);
    }

    @Override
    public void changeMap(Channel channel, int mapId) {
        PlayerEntity player = playerService.getPlayer(channel);

        boolean checkChangeMap = checkChangeMap(player, mapId);
        if(checkChangeMap){
            boolean leaveOldMap = leaveOldMap(player);
            if(leaveOldMap){
                enterNewMap(player, mapId);
            }
        }
    }

    /**
     * 检查玩家能够切图
     *
     */
    private boolean checkChangeMap(PlayerEntity player, int mapId){
        // 检查当前切去的地图跟玩家所在地图是否一样
        if(player.getMapId() == mapId){
            PacketUtils.sendResponse(player.getChannel(), I18nId.YOU_ARE_ON_THE_TARGET_MAP);
            return false;
        }

        // 检查能否切去新地图
        boolean checkChangeMapCondition = SceneType.getSceneById(mapId).getHandler().checkChangeMapCondition(player, player.getMapId());
        if( !checkChangeMapCondition){
            PacketUtils.sendResponse(player, I18nId.DIRECTLY_CHANGE_THE_MAP_ERROR);
            return false;
        }
        return true;
    }

    /**
     * 离开老地图
     *
     */
    private boolean leaveOldMap(PlayerEntity player){
        int oldMap = player.getMapId();
        SceneType oldScene = SceneType.getSceneById(oldMap);
        oldScene.getHandler().doSignOut(player);
        return true;
    }

    /**
     * 进入新地图
     *
     */
    public void enterNewMap(PlayerEntity player, int newMapId){
        SceneType newScece = SceneType.getSceneById(newMapId);
        AbstractMapHandler newSceceHandler = newScece.getHandler();
        newSceceHandler.doEnterMap(player);
        persistenceService.update(player);
    }

    @Override
    public void state(Channel channel) {
        PlayerEntity player = playerService.getPlayer(channel);
        int mapId = player.getMapId();
        SceneType scene = SceneType.getSceneById(mapId);
        scene.getHandler().sendEntityInfo(player);
    }

    @Override
    public void login(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        enterNewMap(player, player.getMapId());
    }

    @Override
    public void signOut(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        SceneType.getSceneById(player.getMapId()).getHandler().doSignOut(player);
    }
}