package com.game.equipment.event;

import com.frame.event.IEvent;
import com.game.account.entity.PlayerEntity;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈脱下装备同步事件〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
public class DeequipmentSyncEvent implements IEvent {

    private PlayerEntity player;

    public static DeequipmentSyncEvent valueOf(PlayerEntity player){
        DeequipmentSyncEvent event = new DeequipmentSyncEvent();
        event.player = player;
        return event;
    }

}