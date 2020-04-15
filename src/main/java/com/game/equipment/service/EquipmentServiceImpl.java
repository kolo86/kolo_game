package com.game.equipment.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.equipment.constant.RepairEquipmentPosition;
import com.game.equipment.constant.EquipmentPosition;
import com.game.equipment.constant.EquipmentType;
import com.game.equipment.entity.EquipmentEntity;
import com.game.equipment.event.DeequipmentSyncEvent;
import com.game.equipment.event.WearEquipmentSyncEvent;
import com.game.equipment.resource.EquipmentResource;
import com.game.item.AbstractItem;
import com.game.item.model.Equipment;
import com.game.item.resource.ItemResource;
import com.game.item.service.ItemManager;
import com.game.packback.entity.BackPackEntity;
import com.game.persistence.service.IPersistenceService;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备服务类〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Component
public class EquipmentServiceImpl implements IEquipmentService {

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IPersistenceService persistenceService;
    @Autowired
    private EquipmentManager equipmentManager;
    @Autowired
    private IEventService eventService;

    @Override
    public void doOpenServer() {
        if (equipmentManager.isInit()) {
            return;
        }

        List<Object> all = persistenceService.getAll(EquipmentEntity.class);
        for (Object obj : all) {
            EquipmentEntity entity = (EquipmentEntity) obj;
            entity.reCalEquipmentAttr();
            equipmentManager.cache(entity);
        }
        equipmentManager.setInit(true);
    }

    @Override
    public void doCreatePlayer(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        EquipmentEntity equipmentEntity = EquipmentEntity.valueOf(accountId);
        player.setEquipmentEntity(equipmentEntity);
        persistenceService.save(equipmentEntity);
    }

    @Override
    public EquipmentEntity getEquipment(String accountId) {
        if (!equipmentManager.isInit()) {
            doOpenServer();
        }

        return equipmentManager.getEntity(accountId);
    }

    @Override
    public void equipment(Channel channel) {
        PlayerEntity player = playerService.getPlayer(channel);
        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<Integer, AbstractItem> equipmentMap = equipmentEntity.getEquipmentMap();

        sendEquipmentInfo(player, equipmentMap);
    }

    /**
     * 发送装备信息
     *
     * @param player
     * @param equipmentMap
     */
    private void sendEquipmentInfo(PlayerEntity player, Map<Integer, AbstractItem> equipmentMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("你已穿戴的装备信息为：\n");
        for (EquipmentPosition position : EquipmentPosition.values()) {
            sb.append(position.getName()).append("【");

            AbstractItem abstractItem = equipmentMap.get(position.getPositionId());
            if (!Objects.isNull(abstractItem)) {
                ItemResource itemResource = ItemManager.getResource(abstractItem.getItemId());
                sb.append("道具ID：").append(abstractItem.getObjectOnlyId()).append(" 名称：").append(itemResource.getName());
                EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(itemResource.getAttrs()));
                if(equipmentResource.getEquipmentType() == EquipmentType.WEAPON.getId()){
                    Equipment equipment = (Equipment) abstractItem;
                    sb.append(" 耐久度为：").append(equipment.getCurrentDurability());
                }
            } else {
                sb.append(" 暂无 ");
            }

            sb.append("】\t");
        }
        PacketUtils.send(player, sb.toString());
    }

    @Override
    public void wear(Channel channel, int objectOnlyId) {
        PlayerEntity player = playerService.getPlayer(channel);
        BackPackEntity backPackEntity = player.getBackPackEntity();
        AbstractItem item = backPackEntity.getItem(objectOnlyId);
        // 检查
        boolean checkWearItem = checkWearItem(player, item);
        if (!checkWearItem) {
            return;
        }
        // 从背包移除道具
        AbstractItem copyItem = item.copy();
        boolean reduceItemResult = backPackEntity.reduceItem(item, 1);
        if (reduceItemResult) {
            persistenceService.update(backPackEntity);

            // 穿戴装备
            EquipmentEntity equipmentEntity = player.getEquipmentEntity();
            equipmentEntity.wear(copyItem);
            persistenceService.update(equipmentEntity);
            // 重新计算玩家的装备属性
            equipmentEntity.reCalEquipmentAttr();

            eventService.submitSyncEvent(WearEquipmentSyncEvent.valueOf(player));

            // 发送最新的穿戴装备信息
            sendEquipmentInfo(player, equipmentEntity.getEquipmentMap());
        }
    }

    /**
     * 检查玩家是否可以穿戴该装备
     */
    private boolean checkWearItem(PlayerEntity player, AbstractItem item) {
        if (Objects.isNull(item)) {
            PacketUtils.send(player, "你当前没有这个装备，无法穿戴！");
            return false;
        } else if (!(item instanceof Equipment)) {
            PacketUtils.send(player, "当前道具不是装备类型，无法穿戴！");
            return false;
        }
        Equipment equipment = (Equipment) item;
        ItemResource itemResource = ItemManager.getResource(equipment.getItemId());
        EquipmentResource equipmentResource = EquipmentManager.getResource(Integer.parseInt(itemResource.getAttrs()));
        if( equipmentResource.getEquipmentType() == EquipmentType.WEAPON.getId() &&
                equipment.isDurabilityZero()){
            PacketUtils.send(player, "当前装备的耐久度为0，无法穿戴！");
            return false;
        }

        return true;
    }

    @Override
    public void deequipment(Channel channel, int objectOnlyId) {
        PlayerEntity player = playerService.getPlayer(channel);
        int position = getDeequipmentPosition(player, objectOnlyId);
        if(position != 0){
            EquipmentEntity equipmentEntity = player.getEquipmentEntity();
            AbstractItem equipment = equipmentEntity.deequipment(position);
            persistenceService.update(equipmentEntity);

            BackPackEntity backPackEntity = player.getBackPackEntity();
            backPackEntity.addItem(equipment);
            persistenceService.update(backPackEntity);

            // 重新计算玩家的装备属性
            equipmentEntity.reCalEquipmentAttr();

            eventService.submitSyncEvent(DeequipmentSyncEvent.valueOf(player));

            sendEquipmentInfo(player, equipmentEntity.getEquipmentMap());
        } else {
            PacketUtils.send(player, "你当前没有穿戴该道具！");
        }
    }

    /**
     * 获取脱下装备的位置ID
     *
     * @param player
     * @param objectOnlyId
     * @return 位置ID，若无法找到，返回0
     */
    private int getDeequipmentPosition(PlayerEntity player, int objectOnlyId){
        int position = 0;

        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<Integer, AbstractItem> equipmentMap = equipmentEntity.getEquipmentMap();
        Set<Map.Entry<Integer, AbstractItem>> entries = equipmentMap.entrySet();
        for(Map.Entry<Integer, AbstractItem> entry : entries){
            AbstractItem abstractItem = entry.getValue();
            if(abstractItem.getObjectOnlyId() == objectOnlyId){
                position =  entry.getKey();
            }
        }
        return position;
    }

    @Override
    public void doWeaponDurabilityZero(PlayerEntity player) {
        // 卸下武器
        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<Integer, AbstractItem> equipmentMap = equipmentEntity.getEquipmentMap();
        AbstractItem abstractItem = equipmentMap.get(EquipmentPosition.WEAPON.getPositionId());
        if(Objects.nonNull(abstractItem)){
            deequipment(player.getChannel(), abstractItem.getObjectOnlyId());
            PacketUtils.send(player, "你当前武器耐久度为0，无法使用，请修理后再重新使用！");
        }
    }

    @Override
    public void repair(Channel channel, int objectOnlyId) {
        PlayerEntity player = playerService.getPlayer(channel);
        Map<Integer, AbstractItem> abstractItemMap = findItem(player, objectOnlyId);
        Map.Entry<Integer, AbstractItem> itemEntry = abstractItemMap.entrySet().iterator().next();
        AbstractItem item = itemEntry.getValue();

        boolean checkRepairItem = checkRepairItem(player, item);
        if ( checkRepairItem ) {
            Equipment weapon = (Equipment) item;
            weapon.recoverDurability();
            // 保存到数据库中
            RepairEquipmentPosition.getPosition(itemEntry.getKey()).save(player);

            PacketUtils.send(channel, "修复武器完成");
        }
    }

    /**
     * 检查修复装备的条件
     *
     * @param player
     * @param item
     * @return
     */
    private boolean checkRepairItem(PlayerEntity player, AbstractItem item){
        if(Objects.isNull(item)){
            PacketUtils.send(player, "修复的道具不存在，请重新输入!");
            return false;
        } else if( !(item instanceof Equipment) ){
            PacketUtils.send(player, "修复的道具不是装备类型，请重新输入！");
            return false;
        }
        return true;
    }

    /**
     * 查看道具
     *
     * @param player
     * @param objectOnlyId
     * @return
     */
    private Map<Integer, AbstractItem> findItem(PlayerEntity player, int objectOnlyId){
        /** Map<该装备所在的位置, 装备> */
        HashMap<Integer, AbstractItem> map = new HashMap<>();

        // 正在穿戴的装备
        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<Integer, AbstractItem> equipmentMap = equipmentEntity.getEquipmentMap();
        Set<Map.Entry<Integer, AbstractItem>> entries = equipmentMap.entrySet();
        for(Map.Entry<Integer, AbstractItem> entry : entries){
            AbstractItem abstractItem = entry.getValue();
            if(abstractItem.getObjectOnlyId() == objectOnlyId){
                map.put(RepairEquipmentPosition.WEARING.getId(), abstractItem);
                return map;
            }
        }

        // 背包中的装备
        BackPackEntity backPackEntity = player.getBackPackEntity();
        Map<Integer, AbstractItem> packMap = backPackEntity.getPackMap();
        map.put(RepairEquipmentPosition.BACKPACK.getId(), packMap.get(objectOnlyId));
        return map;
    }
}