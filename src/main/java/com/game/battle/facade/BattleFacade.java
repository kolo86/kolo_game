package com.game.battle.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.threadpool.battle.BattleExecutor;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.battle.command.BattleMonsterCommand;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br>
 * 〈战斗门面类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Component
public class BattleFacade {
    @Autowired
    private IPlayerService playerService;

    /**
     * 攻击场景中的怪物
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void battleMonster(Channel channel, Message.Attack message){
        PlayerEntity player = playerService.getPlayer(channel);
        BattleExecutor.submit(BattleMonsterCommand.valueOf(player, message.getMonsterId()));
    }

}