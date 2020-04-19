package com.game.item.service;

import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.common.constant.I18nId;
import com.game.item.AbstractItem;
import com.game.item.constant.ItemType;
import com.game.item.resource.ItemResource;
import com.game.backpack.entity.BackPackEntity;
import com.game.persistence.service.IPersistenceService;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 物品服务类
 *
 * @author kolo
 */
@Component
public class ItemServiceImpl implements IItemService {

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IPersistenceService persistenceService;

    @Override
    public List<AbstractItem> ceateItem(int itemId, int num) {
        ItemResource itemResource = ItemManager.getResource(itemId);
        ItemType type = ItemType.getType(itemResource.getType());
        return type.calAndCreateItem(itemId, num);
    }

    @Override
    public void useItem(Channel channel, int itemOnlyId) {
        PlayerEntity player = playerService.getPlayer(channel);
        boolean checkUseItemCondition = checkUseItemCondition(player, itemOnlyId);
        if(!checkUseItemCondition){
            return ;
        }

        BackPackEntity backPackEntity = player.getBackPackEntity();
        AbstractItem item = backPackEntity.getItem(itemOnlyId);
        // 扣除道具
        boolean success = backPackEntity.reduceItem(item, 1);
        persistenceService.update(backPackEntity);

        // 使用道具
        if(success){
            item.useItem(player);
        } else {
            PacketUtils.sendResponse(player, I18nId.FAILED_TO_DEDUCT_PROPS);
        }
    }

    /**
     * 检查使用道具的条件
     *
     */
    private boolean checkUseItemCondition(PlayerEntity player, int itemOnlyId){
        BackPackEntity backPackEntity = player.getBackPackEntity();
        AbstractItem item = backPackEntity.getItem(itemOnlyId);
        if(Objects.isNull(item)){
            PacketUtils.sendResponse(player, I18nId.THERE_IS_NO_SUCH_ITEM_IN_THE_BACKPACK);
            return false;
        }
        if(item.getType() == ItemType.EQUIPMENT.getId()){
            PacketUtils.sendResponse(player, I18nId.USE_WEARABLE_EQUIPMENT_COMMAND);
            return false;
        }
        return true;
    }

}
