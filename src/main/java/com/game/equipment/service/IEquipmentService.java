package com.game.equipment.service;

import com.game.account.entity.PlayerEntity;
import com.game.equipment.entity.EquipmentEntity;
import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备接口〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
public interface IEquipmentService {

    /**
     * 当玩家创建角色的时候
     *
     */
    void doCreatePlayer(String accountId);

    /**
     * 获取玩家的装备信息
     *
     */
    EquipmentEntity getEquipment(String accountId);

    /**
     * 起服时处理的逻辑
     *
     */
    void doOpenServer();

    /**
     * 查看装备栏的装备信息
     *
     */
    void equipment(Channel channel);

    /**
     * 玩家穿装备
     *
     */
    void wear(Channel channel, int objectOnlyId);

    /**
     * 玩家脱装备
     *
     */
    void deequipment(Channel channel, int objectOnlyId);

    /**
     * 处理武器耐久度为0
     *
     */
    void doWeaponDurabilityZero(PlayerEntity player);

    /**
     * 玩家修理武器
     *
     */
    void repair(Channel channel, int objectOnlyId);
}