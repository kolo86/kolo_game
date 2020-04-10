package com.game.battle.util;

import com.game.account.entity.PlayerEntity;
import com.game.battle.constant.BattleConstant;
import com.game.container.constant.AttrType;
import com.game.container.constant.ContainerType;
import com.game.container.model.AttrContainer;
import com.game.container.model.CoolDownContainer;
import com.game.container.model.LifeContainer;
import com.game.container.model.SkillContainer;
import com.game.monster.model.Monster;
import com.game.skill.resource.SkillResource;
import com.game.skill.service.SkillManager;
import com.game.util.PacketUtils;

import java.util.List;
import java.util.Map;

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
     * @param player
     * @param monster
     */
    public static void fightMonster(PlayerEntity player, Monster monster) {
        int selectSkill = selectSkill(player);
        if(selectSkill == 0){
            PacketUtils.send(player, "当前无法使用任何技能，技能均处于CD状态或者蓝量不足！");
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
     * @param player
     * @param skill
     * @param monster
     */
    private static void afterDefenderAttacked(PlayerEntity player, int skill, long skillDamage, Monster monster){
        StringBuilder sb = new StringBuilder();
        SkillResource skillResource = SkillManager.getSkillManager().getSkillResource(skill);
        sb.append("你使用了【").append(skillResource.getName()).append("】技能，对怪物【").append(monster.getName())
                .append("】造成").append(skillDamage).append("点伤害！");
        PacketUtils.send(player, sb.toString());

        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(monster);
        boolean dead = container.isDead();
        if(dead){
            sb.delete(0,sb.length());
            sb.append("玩家【").append(player.getAccountEntity().getNickName()).append("】已经击杀了怪物【").append(monster.getName())
                    .append("】！");

            PacketUtils.sendScene(player, sb.toString());
        }
    };

    /**
     * 在攻击者使用完技能之后
     *
     * @param player
     * @param skill
     */
    private static void afterAttackerUseSkill(PlayerEntity player, int skill) {
        addSkillCd(player, skill);
        reduceAttackerMp(player, skill);
    }

    /**
     * 扣除攻击者的蓝量
     *
     * @param player
     * @param skill
     */
    private static void reduceAttackerMp(PlayerEntity player, int skill) {
        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(player);

        SkillResource skillResource = SkillManager.getSkillManager().getSkillResource(skill);
        container.changeMp(-skillResource.getConsumeMp());
    }

    /**
     * 增加技能CD
     *
     * @param player
     * @param skill
     */
    private static void addSkillCd(PlayerEntity player, int skill) {
        CoolDownContainer container = (CoolDownContainer) ContainerType.COOLDOWN.getContainer(player);

        SkillResource skillResource = SkillManager.getSkillManager().getSkillResource(skill);
        container.cacheSkillCD(skill, System.currentTimeMillis() + skillResource.getCd());
    }

    /**
     * 减少防守方的HP
     *
     * @param skillDamage
     * @param monster
     */
    private static void reduceDefenderHp(long skillDamage, Monster monster) {
        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(monster);
        container.changeHp(-skillDamage);
    }

    /**
     * 玩家使用该技能时，计算技能造成的总伤害
     */
    private static long calSkillDamage(PlayerEntity player, int skill) {
        double skillRatio = getSkillRatio(skill);

        AttrContainer container = (AttrContainer) ContainerType.ATTR.getContainer(player);
        long attackValue = container.getAttrValue(AttrType.ATTACK);
        return (long) (attackValue * skillRatio);
    }

    /**
     * 获取技能伤害比例
     */
    private static double getSkillRatio(int skill) {
        SkillResource skillResource = SkillManager.getSkillManager().getSkillResource(skill);
        Map<AttrType, Long> attrMap = skillResource.getAttrMap();
        return attrMap.getOrDefault(AttrType.SKILL_RATIO, 0L).doubleValue() / BattleConstant.BATTLE_TEN_THOUSAND;
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