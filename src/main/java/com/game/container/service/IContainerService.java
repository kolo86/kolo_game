package com.game.container.service;

import com.game.account.entity.PlayerEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈容器接口〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public interface IContainerService {

    /**
     * 初始化玩家容器
     *
     * @param accountId
     */
    void initPlayerContainer(String accountId);

    /**
     * 执行定时恢复命令
     *
     * @param accountId
     */
    void initRecoverCommand(String accountId);

    /**
     * 穿戴装备事件
     *
     * @param player
     */
    void doWearEquipment(PlayerEntity player);

    /**
     * 重新加载玩家属性
     *
     */
    void reloadPlayerAttr(String accountId);

    /**
     * 处理玩家脱下装备
     *
     * @param player
     */
    void doDeequipment(PlayerEntity player);
}