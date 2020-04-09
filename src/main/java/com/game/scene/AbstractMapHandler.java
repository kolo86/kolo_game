package com.game.scene;

import com.frame.resource.AbstractResource;
import com.frame.resource.handler.ResourceCacheHandler;
import com.game.account.entity.PlayerEntity;
import com.game.container.constant.ContainerType;
import com.game.container.model.LifeContainer;
import com.game.monster.model.Monster;
import com.game.monster.resource.MonsterResource;
import com.game.npc.resource.NpcResource;
import com.game.role.constant.RoleEnum;
import com.game.role.constant.RoleStateEnum;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import lombok.Data;

import java.util.*;
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
    /**
     * 在当前场景中所有账号信息。Map < 账号ID， 账号信息 >
     */
    private Map<String, PlayerEntity> accountMap = new ConcurrentHashMap<>();
    /**
     * Set < NpcId>
     */
    private Set<Integer> npcSet = new HashSet<>();
    /**
     * Map < 怪物名称, 怪物实体  >
     */
    private Map<Integer, Monster> monsterMap = new ConcurrentHashMap<>();

    public AbstractMapHandler() {
        List<AbstractResource> resourceList = ResourceCacheHandler.getAllResource(MonsterResource.class);
        for (AbstractResource resource : resourceList) {
            MonsterResource monsterResource = (MonsterResource) resource;
            Monster monster = Monster.valueOf(monsterResource);
            monsterMap.put(monsterResource.getId(), monster);
        }
    }

    /**
     * 获取场景类型
     *
     * @return 场景类型
     */
    public abstract SceneType getSceneType();

    /**
     * 检查切图条件
     *
     * @return true可以切图，false不能切图
     */
    public boolean checkChangeMapCondition(PlayerEntity player, int oldMapId) {
        return getSceneType().getSceneTypeList().contains(oldMapId);
    }

    /**
     * 玩家进入新地图
     */
    public void doEnterMap(PlayerEntity player) {
        addAccount(player);
        player.setMapId(getSceneType().getMapId());
        sendSuccessMessage(player);
    }

    /**
     * 发送切图成功的提示语给玩家
     */
    public void sendSuccessMessage(PlayerEntity player) {
        StringBuilder sb = new StringBuilder();
        SceneType sceneType = getSceneType();

        sb.append("你已成功进入【");
        sb.append(sceneType.getMapName()).append("】");
        PacketUtils.send(player, sb.toString());

        sendEntityInfo(player);
    }

    /**
     * 获取当前场景的Npc信息
     */
    private String getNpcInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("NPC列表为：");
        for (int npcId : npcSet) {
            NpcResource resource = (NpcResource) ResourceCacheHandler.getResource(NpcResource.class, npcId);
            assert resource != null;
            sb.append(resource.getName()).append("\t");
        }
        return sb.toString();
    }

    /**
     * 给玩家发送当前场景中所有实体的状态
     */
    public void sendEntityInfo(PlayerEntity player) {
        StringBuilder sb = new StringBuilder();
        sb.append("当前地图:【").append(SceneType.getSceneById(player.getMapId()).getMapName())
                .append("】中：\n玩家列表为：");

        for (PlayerEntity tempPlayer : accountMap.values()) {
            sb.append(tempPlayer.getAccountEntity().getNickName())
                    .append("【").append(RoleEnum.getRoleNameById(tempPlayer.getRoleType()))
                    .append("】").append("【")
                    .append(RoleStateEnum.getStateNameById(tempPlayer.getRoleEntity().getRoleState()))
                    .append("】\t");
        }
        sb.append("\n").append(getNpcInfo());
        sb.append("\n").append(getMonsterInfo());
        PacketUtils.send(player, sb.toString());
    }

    /**
     * 获取怪物列表
     */
    private String getMonsterInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("怪物列表为：");
        for( Monster monster : monsterMap.values()){
            LifeContainer container = (LifeContainer)monster.getContainerMap().get(ContainerType.LIFE);
            sb.append(monster.getName()).append("【").append(container.getCurrentHp()).append("/")
                    .append(container.getMaxHp()).append("】").append("【").append(monster.getId())
                    .append("】\t");
        }
        return sb.toString();
    }

    /**
     * 玩家退出旧地图
     */
    public void doSignOut(PlayerEntity player) {
        removeAccount(player);
    }

    /**
     * 在当前场景中，增加进入的账号
     */
    protected void addAccount(PlayerEntity player) {
        accountMap.put(player.getAccountId(), player);
    }

    /**
     * 在当前场景中，移除进入的账号
     */
    protected void removeAccount(PlayerEntity player) {
        accountMap.remove(player.getAccountId());
    }

    /**
     * 检查当前场景中是否存在该NPC
     */
    public boolean checkNpcExist(int npcId) {
        return npcSet.contains(npcId);
    }

    /**
     * 检查当前场景中该怪物是否存在
     *
     */
    public boolean checkMonsterExist(int monsterId){
        Monster monster = monsterMap.get(monsterId);
        if(Objects.isNull(monster)){
            return false;
        }

        LifeContainer lifeContainer = (LifeContainer) ContainerType.LIFE.getContainer(monster);
        boolean dead = lifeContainer.isDead();
        return dead ? false : true;

    }

    /**
     * 根据怪物ID获取怪物
     *
     */
    public Monster getMonsterById(int monsterId){
        return monsterMap.get(monsterId);
    }

}