package com.game.scene;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.account.entity.PlayerEntity;
import com.game.npc.resource.NpcResource;
import com.game.role.constant.RoleEnum;
import com.game.role.constant.RoleStateEnum;
import com.game.role.entity.RoleEntity;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈地图抽象类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
public abstract class AbstractMapHandler {

    /** 在当前场景中所有账号信息。Map < 账号ID， 账号信息 > */
    private Map<String, PlayerEntity> accountMap = new ConcurrentHashMap<String, PlayerEntity>();
    /** Set < NpcId> */
    private Set<Integer> npcSet = new HashSet<Integer>();
    /** Map < 怪物名称, 怪物实体  > */
    private Map<String, RoleEntity> monsterMap = new ConcurrentHashMap<String, RoleEntity>();

    /**
     * 获取场景类型
     *
     * @return
     */
    public abstract SceneType getSceneType();

    /**
     * 检查切图条件
     *
     * @param player
     * @param oldMapId
     * @return
     */
    public boolean checkChangeMapCondition(PlayerEntity player, int oldMapId){
        if(getSceneType().getSceneTypeList().contains(oldMapId)){
            return true;
        }
        return false;
    };

    /**
     * 玩家进入新地图
     *
     * @param player
     */
    public void doEnterMap(PlayerEntity player){
        addAccount(player);
        player.setMapId(getSceneType().getMapId());
        sendSuccessMessage(player);
    };

    /**
     * 发送切图成功的提示语给玩家
     *
     * @param player
     */
    public void sendSuccessMessage(PlayerEntity player){
        StringBuilder sb = new StringBuilder();
        SceneType sceneType = getSceneType();

        sb.append("你已成功进入【");
        sb.append(sceneType.getMapName()).append("】");
        PacketUtils.send(player, sb.toString());

        sendEntityInfo(player);
    }

    /**
     * 获取当前场景的Npc信息
     *
     * @param player
     */
    private String getNpcInfo(PlayerEntity player){
        StringBuilder sb = new StringBuilder();
        sb.append("NPC信息为：");
        if(getNpcSet().size() != 0){
            for( int npcId  : getNpcSet() ){
                NpcResource resource = (NpcResource) ResourceCacheHandler.getResource(NpcResource.class, npcId);
                sb.append(resource.getName()).append("\t");
            }
        }
        return sb.toString();
    }

    /**
     * 给玩家发送当前场景中所有实体的状态
     *
     * @param player
     */
    public void sendEntityInfo(PlayerEntity player){
        StringBuilder sb = new StringBuilder();
        sb.append("当前地图:【").append(SceneType.getSceneById(player.getMapId()).getMapName())
                .append("】中：\n玩家列表为：");

        for( PlayerEntity tempPlayer : accountMap.values()){
            sb.append(tempPlayer.getAccountEntity().getNickName())
                    .append("【").append(RoleEnum.getRoleNameById(tempPlayer.getRoleType()))
                    .append("】").append("【")
                    .append(RoleStateEnum.getStateNameById(tempPlayer.getRoleEntity().getRoleState()))
                    .append("】\t");
        }
        sb.append("\n").append(getNpcInfo(player));
        PacketUtils.send(player, sb.toString());
    }

    /**
     * 玩家退出旧地图
     *
     * @param player
     */
    public void doSignOut(PlayerEntity player){
        removeAccount(player);
    };

    /**
     * 在当前场景中，增加进入的账号
     *
     * @param player
     */
    protected void addAccount(PlayerEntity player){
        accountMap.put(player.getAccountId(), player);
    }

    /**
     * 在当前场景中，移除进入的账号
     *
     * @param player
     */
    protected void removeAccount(PlayerEntity player){
        accountMap.remove(player.getAccountId());
    }

    /**
     * 检查当前场景中是否存在该NPC
     *
     * @param npcId
     * @return
     */
    public boolean checkNpcExist(int npcId){
        return npcSet.contains(npcId);
    }
}