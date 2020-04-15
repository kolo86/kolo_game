package com.game.drop.handler;

import com.game.account.entity.PlayerEntity;
import com.game.drop.resource.DropResource;
import com.game.item.AbstractItem;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈抽象的掉落处理器〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public abstract class AbstractDropHandler {

    /**
     * 命中道具
     *
     * @param player
     * @param dropResource
     * @return
     */
    public abstract List<AbstractItem> hitItem(PlayerEntity player, DropResource dropResource);

}