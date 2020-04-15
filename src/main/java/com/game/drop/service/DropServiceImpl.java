package com.game.drop.service;

import com.game.account.entity.PlayerEntity;
import com.game.drop.constant.DropType;
import com.game.drop.resource.DropResource;
import com.game.item.AbstractItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈掉落服务〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Component
public class DropServiceImpl implements IDropService{

    @Override
    public List<AbstractItem> hitItem(PlayerEntity player, int dropId) {
        DropResource dropResource = DropManager.getResource(dropId);
        DropType dropType = DropType.getDropType(dropResource.getType());
        return dropType.hitItem(player, dropResource);
    }
}