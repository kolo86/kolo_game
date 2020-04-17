package com.game.battle.event;

import com.frame.event.IEvent;
import com.game.account.entity.PlayerEntity;
import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈武器耐久度为0事件〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Data
public class WeaponDurabilityZeroEvent implements IEvent {

    private PlayerEntity player;

    public static WeaponDurabilityZeroEvent valueOf(PlayerEntity player){
        WeaponDurabilityZeroEvent event = new WeaponDurabilityZeroEvent();
        event.player = player;
        return event;
    }

    @Override
    public int getKey() {
        return player.getAccountId().hashCode();
    }
}