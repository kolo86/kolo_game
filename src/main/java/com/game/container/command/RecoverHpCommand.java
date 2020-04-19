package com.game.container.command;

import com.frame.threadpool.account.AbstractScheduleAccountCommand;
import com.game.account.entity.PlayerEntity;
import com.game.container.constant.ContainerType;
import com.game.container.model.LifeContainer;
import com.game.util.PacketUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈恢复血量命令〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public class RecoverHpCommand extends AbstractScheduleAccountCommand {

    private PlayerEntity player;

    private long recoverHp;

    /** 失效时间 */
    private long invalidTime;

    RecoverHpCommand(PlayerEntity player, long initDelay, long delay){
        super(player.getAccountId(), initDelay, delay);
    }

    public static RecoverHpCommand valueOf(PlayerEntity player, long initDelay, int delay, long recoverHp){
        RecoverHpCommand command = new RecoverHpCommand(player, initDelay, delay);
        command.player = player;
        command.recoverHp = recoverHp;
        return command;
    }

    public static RecoverHpCommand valueOf(PlayerEntity player, long initDelay, int delay, long recoverHp, long invalidTime){
        RecoverHpCommand command = new RecoverHpCommand(player, initDelay, delay);
        command.player = player;
        command.recoverHp = recoverHp;
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
        long changeHp = container.changeHp(recoverHp);
        if( changeHp != 0 ){
            PacketUtils.sendResponse(player, "你恢复了" + changeHp + "点Hp!");
        } else if( invalidTime == 0 ){
            // 对于自动恢复类型，血量满了就不再执行
            this.cancel();
        }
    }

}