package com.game.container.service;

import com.frame.threadpool.account.AccountScheduleExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.account.resource.PlayerResource;
import com.game.account.service.IPlayerService;
import com.game.account.service.PlayerManager;
import com.game.container.command.RecoverMpCommand;
import com.game.container.constant.AttrType;
import com.game.container.constant.ContainerType;
import com.game.container.model.AttrContainer;
import com.game.equipment.entity.EquipmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈容器服务类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Component
public class ContainerService implements IContainerService {

    @Autowired
    private IPlayerService playerService;

    @Override
    public void initPlayerContainer(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        player.initContainer();

        reloadPlayerAttr(accountId);
    }

    @Override
    public void initRecoverCommand(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        PlayerResource playerResource = PlayerManager.getPlayerManager().getPlayerResource(player.getRoleType());
        // MP
        RecoverMpCommand command = RecoverMpCommand.valueOf(player, 1000, 1000, playerResource.getRecoverMp());
        AccountScheduleExecutor.submit(command);
        player.cacheCommand(command);
    }

    @Override
    public void doWearEquipment(PlayerEntity player) {
        reloadPlayerAttr(player.getAccountId());
    }

    /**
     * 重新计算玩家最终属性
     *
     */
    private void reloadPlayerFinalAttr(PlayerEntity player){
        AttrContainer attrContainer = (AttrContainer) ContainerType.ATTR.getContainer(player);
        attrContainer.calFinalAttr();
    }

    /**
     * 重新计算玩家临时属性
     *
     */
    private void reloadPlayerTempAttr(PlayerEntity player){
        AttrContainer attrContainer = (AttrContainer)ContainerType.ATTR.getContainer(player);
        EquipmentEntity equipmentEntity = player.getEquipmentEntity();
        Map<AttrType, Long> equipmentAttrMap = equipmentEntity.getEquipmentAttrMap();
        attrContainer.coverTempAttr(equipmentAttrMap);
    }

    @Override
    public void reloadPlayerAttr(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        // 重新计算玩家临时属性
        reloadPlayerTempAttr(player);
        // 重新计算玩家最终属性
        reloadPlayerFinalAttr(player);
    }

    @Override
    public void doDeequipment(PlayerEntity player) {
        reloadPlayerAttr(player.getAccountId());
    }
}