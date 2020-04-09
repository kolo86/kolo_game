package com.game.monster.model;

import com.game.container.AbstractContainer;
import com.game.container.constant.ContainerType;
import com.game.monster.resource.MonsterResource;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈怪物实体〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
public class Monster {
    /** 怪物ID */
    private int id;
    /** 怪物名字 */
    private String name;
    /** 怪物的属性容器  */
    private Map<ContainerType, AbstractContainer> containerMap = new ConcurrentHashMap<>();

    /**
     * 根据怪物配置表初始化怪物信息
     *
     * @param monsterResource
     * @return
     */
    public static Monster valueOf(MonsterResource monsterResource){
        Monster monster = new Monster();
        monster.id = monsterResource.getId();
        monster.name = monsterResource.getName();
        monster.containerMap = ContainerType.initMonsterContainer(monsterResource);
        return monster;
    }

}