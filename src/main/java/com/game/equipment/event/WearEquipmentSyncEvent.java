package com.game.equipment.event;

import com.frame.event.IEvent;
import com.game.account.entity.PlayerEntity;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈穿戴装备事件〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
public class WearEquipmentSyncEvent implements IEvent {

    private PlayerEntity player;

    public static WearEquipmentSyncEvent valueOf(PlayerEntity player){
        WearEquipmentSyncEvent event = new WearEquipmentSyncEvent();
        event.player = player;
        return event;
    }

    @Override
    public int getKey() {
        return player.getAccountId().hashCode();
    }
}