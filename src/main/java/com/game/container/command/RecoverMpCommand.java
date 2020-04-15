package com.game.container.command;

import com.frame.threadpool.account.AbstractScheduleAccountCommand;
import com.game.account.entity.PlayerEntity;
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

    private long recoverMp;

    /** 失效时间 */
    private long invalidTime;

    RecoverMpCommand(PlayerEntity player, long initDelay, long delay){
        super(player.getAccountId(), initDelay, delay);
    }

    public static RecoverMpCommand valueOf(PlayerEntity player, long initDeday, int delay, long recoverMp){
        RecoverMpCommand command = new RecoverMpCommand(player, initDeday, delay);
        command.player = player;
        command.recoverMp = recoverMp;
        return command;
    }

    public static RecoverMpCommand valueOf(PlayerEntity player, long initDeday, int delay, long recoverMp, long invalidTime){
        RecoverMpCommand command = new RecoverMpCommand(player, initDeday, delay);
        command.player = player;
        command.recoverMp = recoverMp;
        command.invalidTime = invalidTime;
        return command;
    }

    @Override
    public void action() {
        if(invalidTime != 0 && System.currentTimeMillis() > invalidTime){
            this.cancel();
            return ;
        }

        LifeContainer container = (LifeContainer) ContainerType.LIFE.getContainer(player);
        container.changeMp(recoverMp);
    }
}