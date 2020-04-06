package com.game.signout.service;

import io.netty.channel.Channel;

/**
 * 玩家退出模块接口
 *
 * @author kolo
 */
public interface ISignOutService {

    /**
     * 玩家退出游戏
     *
     * @param channel
     */
    void signOut(Channel channel);
}
