package com.game.login.service;

import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈登录接口〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface ILoginService {

    /**
     * 玩家登录
     *
     */
    void login(Channel channel, String account);

    /**
     * 创建账号完成后，登录
     *
     */
    void createAccountToLogin(String accountId);

}