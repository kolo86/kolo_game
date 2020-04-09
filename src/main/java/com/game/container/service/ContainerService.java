package com.game.container.service;

import com.frame.threadpool.account.AccountScheduleExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.container.command.RecoverMpCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    }

    @Override
    public void initRecoverCommand(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        AccountScheduleExecutor.submit(RecoverMpCommand.valueOf(player, 1000, 1000) );
    }
}