package com.game.battle.service;


import com.game.account.entity.PlayerEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈攻击接口类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public interface IBattleService {

    /**
     * 攻击场景中的怪物
     *
     * @param channel
     * @param monster
     */
    void battleMonster(PlayerEntity player, int monster);

}