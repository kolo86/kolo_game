package com.game.battle.facade;

import com.frame.dispatcher.anno.ReceiverAnno;
import com.game.battle.service.IBattleService;
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
    private IBattleService battleService;

    /**
     * 攻击场景中的怪物
     *
     * @param channel
     * @param message
     */
    @ReceiverAnno
    public void battleMonster(Channel channel, Message.Attack message){
        battleService.battleMonster(channel, message.getMonsterId());
    }

}