package com.game.battle.util;

import com.frame.event.service.EventServiceImpl;
import com.frame.threadpool.account.AccountExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.battle.command.RewardMonsterCommand;
import com.game.battle.constant.BattleConstant;
import com.game.battle.event.WeaponDurabilityZeroEvent;
import com.game.common.SpringContext;
import com.game.common.constant.I18nId;
import com.game.container.constant.AttrType;
import com.game.container.constant.ContainerType;
import com.game.container.model.AttrContainer;
import com.game.container.model.CoolDownContainer;
import com.game.container.model.LifeContainer;
import com.game.container.model.SkillContainer;
import com.game.drop.service.DropServiceImpl;
import com.game.equipment.constant.EquipmentPosition;
import com.game.equipment.entity.EquipmentEntity;
import com.game.item.AbstractItem;
import com.game.item.model.Equipment;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.monster.model.Monster;
import com.game.persistence.service.PersistenceServiceImpl;
import com.game.skill.resource.SkillResource;
import com.game.skill.service.SkillManager;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈战斗工具类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public class FightUtils {

    /**
     * 玩家挑战一个怪物
     *
     */
    public static void fightMonster(PlayerEntity player, Monster monster) {
        int selectSkill = selectSkill(player);
        if(selectSkill == 0){
            PacketUtils.sendResponse(player, I18nId.SKILL_UNAVAILABLE);
            return ;
        }

        long skillDamage = calSkillDamage(player, selectSkill);
        reduceDefenderHp(skillDamage, monster);

        afterAttackerUseSkill(player, selectSkill);
        afterDefenderAttacked(player, selectSkill, skillDamage, monster);
    }

    /**
     * 当防守方被攻击之后
     *
     */
    private static void afterDefenderAttacked(PlayerEntity player, int skill, long skillDamage, Monster monster){

        Message.Sm_Attack smAttack = Message.Sm_Attack.newBuilder().setSkillId(skill).setMonsterName(monster.getName()).setDamage(skillDamage).build();
        PacketUtils.send(player, ProtocolEnum.Sm_Attack.getId(), smAttack.toByteArray());

        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(monster);
        boolean dead = container.isDead();
        if(dead){
            Message.Sm_MonsterDead smMonsterDead = Message.Sm_MonsterDead.newBuilder().setKiller(player.getAccountEntity().getNickName()).setMonster(monster.getName()).build();
            PacketUtils.sendScene(player, ProtocolEnum.Sm_MonsterDead.getId(), smMonsterDead.toByteArray());

            // 怪物死亡，发放怪物奖励
            List<AbstractItem> itemList = awardMonster(player, monster);
            // 发送掉落奖励消息给玩家
            sendDropAwardMessage(player, itemList);
        }
    }

    /**
     * 给玩家发放怪物奖励
     *
     */
    private static List<AbstractItem> awardMonster(PlayerEntity player, Monster monster){
        DropServiceImpl dropService = (DropServiceImpl)SpringContext.getBean(DropServiceImpl.class);
        List<AbstractItem> abstractItems = dropService.hitItem(player, monster.getMonsterDropId());
        AccountExecutor.submit(RewardMonsterCommand.valueOf(player, abstractItems));
        return abstractItems;
    }

    /**
     * 发送掉落奖励给玩家
     *
     */
    private static void sendDropAwardMessage(PlayerEntity player, List<AbstractItem> itemList){
        Message.Sm_KillerReward.Builder smKillerRewardbuilder = Message.Sm_KillerReward.newBuilder();

        for (AbstractItem item : itemList) {
            ItemResource itemResource = ItemManager.getResource(item.getItemId());
            Message.Item messageItem = Message.Item.newBuilder().setItemId(item.getItemId()).setItemName(itemResource.getName()).setItemNum(item.getNum())
                    .setItemOnlyId(item.getItemId()).build();
            smKillerRewardbuilder.addReward(messageItem);
        }
        Message.Sm_KillerReward smKillerReward = smKillerRewardbuilder.build();
        PacketUtils.send(player, ProtocolEnum.Sm_KillerReward.getId(),smKillerReward.toByteArray());
    }

    /**
     * 在攻击者使用完技能之后
     *
     */
    private static void afterAttackerUseSkill(PlayerEntity player, int skill) {
        addSkillCd(player, skill);
        reduceAttackerMp(player, skill);
        reduceWeaponDurability(player);
    }

    /**
     * 减少武器耐久度
     *
     */
    private static void reduceWeaponDurability(PlayerEntity player){
        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<Integer, AbstractItem> equipmentMap = equipmentEntity.getEquipmentMap();
        AbstractItem abstractItem = equipmentMap.get(EquipmentPosition.WEAPON.getPositionId());
        if( !Objects.isNull(abstractItem) && abstractItem instanceof Equipment){
            Equipment weaponItem = (Equipment)abstractItem;
            weaponItem.reduceDurability(BattleConstant.WEAPON_WASTAGE);

            //若武器耐久度为0，则抛出耐久度为0事件，把武器卸下来
            if(weaponItem.isDurabilityZero()){
                EventServiceImpl eventService = (EventServiceImpl)SpringContext.getBean(EventServiceImpl.class);
                eventService.submitSyncEvent(WeaponDurabilityZeroEvent.valueOf(player));
            } else {
                PersistenceServiceImpl persistenceService = (PersistenceServiceImpl)SpringContext.getBean(PersistenceServiceImpl.class);
                persistenceService.update(equipmentEntity);
            }
        }
    }

    /**
     * 扣除攻击者的蓝量
     *
     */
    private static void reduceAttackerMp(PlayerEntity player, int skill) {
        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(player);

        SkillResource skillResource = SkillManager.getSkillResource(skill);
        container.changeMp(-skillResource.getConsumeMp());
    }

    /**
     * 增加技能CD
     *
     */
    private static void addSkillCd(PlayerEntity player, int skill) {
        CoolDownContainer container = (CoolDownContainer) ContainerType.COOLDOWN.getContainer(player);

        SkillResource skillResource = SkillManager.getSkillResource(skill);
        container.cacheSkillCD(skill, System.currentTimeMillis() + skillResource.getCd());
    }

    /**
     * 减少防守方的HP
     *
     */
    private static void reduceDefenderHp(long skillDamage, Monster monster) {
        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(monster);
        container.changeHp(-skillDamage);
    }

    /**
     * 玩家使用该技能时，计算技能造成的总伤害
     */
    private static long calSkillDamage(PlayerEntity player, int skill) {
        double skillRatio = getSkillRatio(player, skill);

        AttrContainer container = (AttrContainer) ContainerType.ATTR.getContainer(player);
        long attackValue = container.getAttrValue(AttrType.ATTACK);
        return (long) (attackValue * skillRatio);
    }

    /**
     * 获取技能伤害比例
     */
    private static double getSkillRatio(PlayerEntity player, int skill) {
        // 技能配置表的技能伤害比例
        SkillResource skillResource = SkillManager.getSkillResource(skill);
        Map<AttrType, Long> attrMap = skillResource.getAttrMap();
        double resourceSkillRatio = attrMap.getOrDefault(AttrType.SKILL_RATIO, 0L).doubleValue();

        // 玩家的技能伤害比例属性
        AttrContainer attrContainer = (AttrContainer) ContainerType.ATTR.getContainer(player);
        long attrSkillRatio = attrContainer.getAttrValue(AttrType.SKILL_RATIO);

        return (resourceSkillRatio + attrSkillRatio) / BattleConstant.BATTLE_TEN_THOUSAND;
    }

    /**
     * 获取玩家当前可以使用的技能
     */
    private static int selectSkill(PlayerEntity player) {
        // 技能容器
        SkillContainer skillContainer = (SkillContainer) ContainerType.SKILL.getContainer(player);
        List<Integer> skillList = skillContainer.getSkillList();
        // 技能冷却容器
        CoolDownContainer coolDownContainer = (CoolDownContainer) ContainerType.COOLDOWN.getContainer(player);
        // 生命容器
        LifeContainer lifeContainer = (LifeContainer) ContainerType.LIFE.getContainer(player);

        int skill = 0;
        for (Integer tempSkill : skillList) {
            if (coolDownContainer.checkSkill(tempSkill) &&
                    lifeContainer.isMpEnough(tempSkill)) {
                skill = tempSkill;
                break;
            }
        }

        return skill;
    }

}