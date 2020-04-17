package com.game.container.constant;

import com.game.account.entity.PlayerEntity;
import com.game.account.resource.PlayerResource;
import com.game.container.AbstractContainer;
import com.game.container.model.AttrContainer;
import com.game.container.model.CoolDownContainer;
import com.game.container.model.LifeContainer;
import com.game.container.model.SkillContainer;
import com.game.monster.model.Monster;
import com.game.monster.resource.MonsterResource;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈容器类型〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
public enum ContainerType {
        /** 生命容器 */
        LIFE("生命"){
            @Override
            public AbstractContainer initForMonster(MonsterResource resource) {
                Map<AttrType, Long> attrMap = resource.getAttrMap();
                Long maxHp = attrMap.getOrDefault(AttrType.MAX_HP, 0L);
                Long maxMp = attrMap.getOrDefault(AttrType.MAX_MP, 0L);
                return LifeContainer.valueOf(maxHp, maxMp);
            }

            @Override
            public AbstractContainer initForPlayer(PlayerResource playerResource) {
                Map<AttrType, Long> attrMap = playerResource.getAttrMap();
                Long maxHp = attrMap.getOrDefault(AttrType.MAX_HP, 0L);
                Long maxMp = attrMap.getOrDefault(AttrType.MAX_MP, 0L);
                return LifeContainer.valueOf(maxHp, maxMp);
            }
        },
        /** 冷却容器 */
        COOLDOWN("冷却"){
            @Override
            public AbstractContainer initForMonster(MonsterResource resource) {
                return CoolDownContainer.valueOf();
            }

            @Override
            public AbstractContainer initForPlayer(PlayerResource playerResource) {
                return CoolDownContainer.valueOf();
            }
        },
        /** 技能容器 */
        SKILL("技能"){
            @Override
            public AbstractContainer initForMonster(MonsterResource resource) {
                return SkillContainer.valueOf(new ArrayList<>());
            }

            @Override
            public AbstractContainer initForPlayer(PlayerResource playerResource) {
                return SkillContainer.valueOf(playerResource.getSkillList());
            }
        },
        /** 属性容器 */
        ATTR("属性"){
            @Override
            public AbstractContainer initForMonster(MonsterResource resource) {
                return AttrContainer.valueOf(resource.getAttrMap());
            }

            @Override
            public AbstractContainer initForPlayer(PlayerResource playerResource) {
                return AttrContainer.valueOf(playerResource.getAttrMap());
            }
        },
    ;

    /** 容器名称 */
    private String name;

    ContainerType(String name){
        this.name = name;
    }

    /**
     * 初始化怪物容器
     *
     */
    public static Map<ContainerType, AbstractContainer> initMonsterContainer(MonsterResource resource){
        Map<ContainerType, AbstractContainer> map = new ConcurrentHashMap<>();
        for(ContainerType type : values()){
            AbstractContainer container = type.initForMonster(resource);
            map.put(type, container);
        }
        return map;
    }

    /**
     * 初始化怪物容器
     *
     */
    public abstract AbstractContainer initForMonster(MonsterResource resource);

    /**
     * 根据类型获取怪物的容器
     *
     */
    public AbstractContainer getContainer(Monster monster) {
        return monster.getContainerMap().get(this);
    }

    /**
     * 初始化角色容器
     *
     */
    public abstract AbstractContainer initForPlayer(PlayerResource playerResource);

    /**
     * 初始化玩家容器
     *
     */
    public static Map<ContainerType, AbstractContainer> initPlayerContainer(PlayerResource resource){
        Map<ContainerType, AbstractContainer> map = new ConcurrentHashMap<>();
        for(ContainerType type : values()){
            AbstractContainer container = type.initForPlayer(resource);
            map.put(type, container);
        }
        return map;
    }

    /**
     * 根据类型获取玩家的容器
     *
     */
    public AbstractContainer getContainer(PlayerEntity player) {
        return player.getContainerMap().get(this);
    }
}