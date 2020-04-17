package com.game.item.service;

import com.game.item.AbstractItem;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 物品接口
 *
 * @author kolo
 */
public interface IItemService {

    /**
     * 根据道具ID创建Item
     *
     */
    List<AbstractItem> ceateItem(int itemId, int num);

    /**
     * 玩家使用道具
     *
     */
    void useItem(Channel channel, int itemOnlyId);

}
