/**
 * FileName: ILoginService
 * Author:   坤龙
 * Date:     2020/4/2 15:24
 * Description: 登录接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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
     * @param channel
     * @param account
     */
    void login(Channel channel, String account);

    /**
     * 创建账号完成后，登录
     *
     * @param accountId
     */
    void createAccountToLogin(String accountId);

}