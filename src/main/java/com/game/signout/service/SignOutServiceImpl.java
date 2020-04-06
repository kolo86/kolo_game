package com.game.signout.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.signout.event.SignOutEvent;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 玩家退出实现类
 * @author KOLO
 */
@Component
public class SignOutServiceImpl implements ISignOutService {

    private static final Logger logger = LoggerFactory.getLogger(SignOutServiceImpl.class);

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IEventService eventService;

    @Override
    public void signOut(Channel channel) {
        PlayerEntity player = playerService.getPlayer(channel);
        player.signOut();

        PacketUtils.send(channel, "退出游戏成功！");

        eventService.submitAsyncEvent(SignOutEvent.valueOf(player.getAccountId()));
    }
}
