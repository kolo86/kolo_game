package com.game.packback.service;

import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.item.AbstractItem;
import com.game.packback.entity.BackPackEntity;
import com.game.persistence.service.IPersistenceService;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 背包实现类
 *
 */
@Component
public class BackpackServiceImpl implements IBackPackService {

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IPersistenceService persistenceService;
    @Autowired
    private BackpackManager backpackManager;

    @Override
    public void doCreateRole(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        BackPackEntity backPackEntity = BackPackEntity.valueOf(player.getAccountId());
        player.setBackPackEntity(backPackEntity);

        backpackManager.cacheBackpack(backPackEntity);
        persistenceService.save(backPackEntity);
    }

    @Override
    public void openServer() {
        if(backpackManager.isInit()){
           return ;
        }

        List<Object> all = persistenceService.getAll(BackPackEntity.class);
        for(Object obj : all){
            BackPackEntity entity = (BackPackEntity)obj;
            backpackManager.cacheBackpack(entity);
        }

        backpackManager.setInit(true);
    }

    @Override
    public BackPackEntity getBackpack(String accountId) {
        if(!backpackManager.isInit()){
            openServer();
        }

        return backpackManager.getEntity(accountId);
    }

    @Override
    public void backPack(Channel channel) {
        PlayerEntity player = playerService.getPlayer(channel);
        sendBackpackInfo(player);
    }

    /**
     * 发送背包数据给玩家
     *
     * @param player
     */
    private void sendBackpackInfo(PlayerEntity player){
        BackPackEntity backPackEntity = player.getBackPackEntity();
        Map<Integer, AbstractItem> packMap = backPackEntity.getPackMap();

        StringBuilder sb = new StringBuilder();
        sb.append("你的背包信息为：");
        for(  AbstractItem item : packMap.values()){
            sb.append("\n").append(item.getItemType().getItemDetail(item));
        }

        PacketUtils.send(player, sb.toString());
    }

    @Override
    public void addItems(PlayerEntity player, List<AbstractItem> list) {
        BackPackEntity backPackEntity = player.getBackPackEntity();
        for(AbstractItem item : list){
            backPackEntity.addItem(item);
        }
        persistenceService.update(backPackEntity);
    }
}
