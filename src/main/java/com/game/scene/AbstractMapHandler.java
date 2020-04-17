package com.game.scene;

import com.frame.resource.AbstractResource;
import com.frame.resource.handler.ResourceCacheHandler;
import com.game.account.entity.PlayerEntity;
import com.game.common.constant.I18nId;
import com.game.container.constant.ContainerType;
import com.game.container.model.LifeContainer;
import com.game.monster.model.Monster;
import com.game.monster.resource.MonsterResource;
import com.game.npc.resource.NpcResource;
import com.game.role.constant.RoleEnum;
import com.game.role.constant.RoleStateEnum;
import com.game.scene.constant.SceneType;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(AbstractMapHandler.class);
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
        if( Objects.isNull(resourceList) ){
            logger.error("MonsterResource为空！无法初始化怪物信息！");
            return ;
        }
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
        Message.Sm_ChangeMap smChangeMap = Message.Sm_ChangeMap.newBuilder().setSuccess(true).build();
        PacketUtils.send(player, ProtocolEnum.Sm_ChangeMap.getId(),smChangeMap.toByteArray());

        sendEntityInfo(player);
    }

    /**
     * 给玩家发送当前场景中所有实体的状态
     */
    public void sendEntityInfo(PlayerEntity player) {
        Message.Sm_State.Builder stateBuilder = Message.Sm_State.newBuilder();
        // 场景信息
        stateBuilder.setMapName(SceneType.getSceneById(player.getMapId()).getMapName());
        // 用户信息
        List<Message.UserInfo> userList = getUserList();
        stateBuilder.addAllUser(userList);
        // 怪物信息
        List<Message.monsterInfo> monsterInfoList = getMonsterInfo();
        stateBuilder.addAllMonster(monsterInfoList);
        // NPC信息
        List<Message.NpcInfo> npcInfo = getNpcInfo();
        stateBuilder.addAllNpc(npcInfo);

        Message.Sm_State smState = stateBuilder.build();
        PacketUtils.send(player, ProtocolEnum.Sm_State.getId(), smState.toByteArray());
    }

    /**
     * 获取玩家列表
     *
     */
    private List<Message.UserInfo> getUserList(){
        List<Message.UserInfo> userList = new ArrayList<>();
        accountMap.values().forEach(tempPlayer -> {
            Message.UserInfo userInfo = Message.UserInfo.newBuilder().setNickName(tempPlayer.getAccountEntity().getNickName())
                    .setRoleType(RoleEnum.getRoleNameById(tempPlayer.getRoleType()))
                    .setLiveState(RoleStateEnum.getStateNameById(tempPlayer.getRoleEntity().getRoleState()))
                    .build();
            userList.add(userInfo);
        });
        return userList;
    }

    /**
     * 获取怪物列表
     */
    private List<Message.monsterInfo> getMonsterInfo() {
        List<Message.monsterInfo> monsterList = new ArrayList<>();
        for( Monster monster : monsterMap.values()){
            LifeContainer container = (LifeContainer)monster.getContainerMap().get(ContainerType.LIFE);
            Message.monsterInfo monsterInfo = Message.monsterInfo.newBuilder().setMonsterName(monster.getName()).setCurrentHp(container.getCurrentHp().get())
                    .setMaxHp(container.getMaxHp()).setMonsterId(monster.getId()).build();
            monsterList.add(monsterInfo);
        }
        return monsterList;
    }

    /**
     * 获取当前场景的Npc信息
     */
    private List<Message.NpcInfo> getNpcInfo() {
        List<Message.NpcInfo> npcInfoList = new ArrayList<>();
        for (int npcId : npcSet) {
            NpcResource resource = (NpcResource) ResourceCacheHandler.getResource(NpcResource.class, npcId);
            npcInfoList.add(Message.NpcInfo.newBuilder().setNpcName(resource.getName()).build());
        }
        return npcInfoList;
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