package com.game.drop.handler.impl;

import com.game.account.entity.PlayerEntity;
import com.game.common.SpringContext;
import com.game.drop.handler.AbstractDropHandler;
import com.game.drop.model.SelectItem;
import com.game.drop.resource.DropResource;
import com.game.drop.util.DropUtils;
import com.game.item.AbstractItem;
import com.game.item.service.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈随机掉落选择器〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public class RandomDropHandler extends AbstractDropHandler {

    /**
     * 命中道具
     *
     */
    @Override
    public List<AbstractItem> hitItem(PlayerEntity player, DropResource dropResource){
        List<AbstractItem> list = new ArrayList<>();

        List<SelectItem> itemList = dropResource.getItemList();
        for( SelectItem item : itemList ){
            double prob = item.getProb();
            int itemId = item.getItemId();
            int hitNum = DropUtils.hitNum(prob);
            if(hitNum > 0){
                ItemServiceImpl itemService = (ItemServiceImpl)SpringContext.getBean(ItemServiceImpl.class);
                list.addAll(itemService.ceateItem(itemId, hitNum ));
            }
        }
        return list;
    }

}