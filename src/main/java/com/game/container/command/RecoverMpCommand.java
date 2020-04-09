package com.game.container.command;

import com.frame.threadpool.account.AbstractScheduleAccountCommand;
import com.game.account.entity.PlayerEntity;
import com.game.account.resource.PlayerResource;
import com.game.account.service.PlayerManager;
import com.game.container.constant.ContainerType;
import com.game.container.model.LifeContainer;

/**
 * 〈一句话功能简述〉<br>
 * 〈恢复蓝量命令〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public class RecoverMpCommand extends AbstractScheduleAccountCommand {

    private PlayerEntity player;

    RecoverMpCommand(PlayerEntity player, long initDelay, long delay){
        super(player.getAccountId(), initDelay, delay);
    }

    public static RecoverMpCommand valueOf(PlayerEntity player, long initDeday, int delay){
        RecoverMpCommand command = new RecoverMpCommand(player, initDeday, delay);
        command.player = player;
        return command;
    }

    @Override
    public void action() {
        if( !player.isOnline() ){
            return;
        }

        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(player);
        PlayerResource playerResource = PlayerManager.getPlayerManager().getPlayerResource(player.getRoleType());
        container.changeMp(playerResource.getRecoverMp());
    }
}