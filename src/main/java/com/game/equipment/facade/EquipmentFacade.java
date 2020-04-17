package com.game.equipment.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.event.anno.EventAnno;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.account.event.CreateAccountAsyncEvent;
import com.game.battle.event.WeaponDurabilityZeroEvent;
import com.game.equipment.service.IEquipmentService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈装备门面类〉
 *
 * @author KOLO
 * @create 2020/4/14
 * @since 1.0.0
 */
@Component
public class EquipmentFacade {

    @Autowired
    private IEquipmentService equipmentService;

    /**
     * 查看装备栏信息
     *
     * @param channel
     * @param equipment
     */
    @ReceiverAnno
    public void equipment(Channel channel, Message.Cm_Equipment equipment){
        equipmentService.equipment(channel);
    }

    /**
     * 玩家穿装备
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void wear(Channel channel, Message.Cm_Wear message){
        equipmentService.wear(channel, message.getEquipmentId());
    }

    /**
     * 玩家脱装备
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void deequipment(Channel channel, Message.Cm_Deequipment message){
        equipmentService.deequipment(channel, message.getEquipmentId());
    }

    /**
     * 玩家修理武器
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void repair(Channel channel, Message.Cm_Repair message){
        equipmentService.repair(channel, message.getEquipmentId());
    }

    /**
     * 创建角色完成后，初始化玩家装备信息
     *
     * @param event
     */
    @EventAnno
    public void doCreatePlayer(CreateAccountAsyncEvent event){
        equipmentService.doCreatePlayer(event.getAccount());
    }


    /**
     * 监听起服事件
     *
     * @param event
     */
    @EventAnno
    public void doOpenServer(OpenServerSyncEvent event){
        equipmentService.doOpenServer();
    }

    /**
     * 处理武器耐久度为0事件
     *
     * @param event
     */
    @EventAnno
    public void doWeaponDurabilityZero(WeaponDurabilityZeroEvent event){
        equipmentService.doWeaponDurabilityZero(event.getPlayer());
    }
}