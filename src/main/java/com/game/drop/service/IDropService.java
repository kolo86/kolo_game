package com.game.drop.service;

import com.game.account.entity.PlayerEntity;
import com.game.item.AbstractItem;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落接口〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public interface IDropService {

    /**
     * 根据掉落选择器命中道具
     *
     */
    List<AbstractItem> hitItem(PlayerEntity player, int dropId);

}