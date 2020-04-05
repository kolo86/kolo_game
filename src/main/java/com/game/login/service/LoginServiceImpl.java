/**
 * FileName: LoginService
 * Author:   坤龙
 * Date:     2020/4/2 15:25
 * Description: 登录实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.login.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IAccountService;
import com.game.account.service.IPlayerService;
import com.game.login.event.CreateRoleLoginEvent;
import com.game.login.event.LoginEvent;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈登录实现类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class LoginServiceImpl implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private IAccountService accountService;

    @Override
    public void createAccountToLogin(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        if (player == null) {
            logger.error("玩家{}创建角色后，登录失败，无法根据账号ID获取账号信息", accountId);
        }

        player.online();
        PacketUtils.send(player, "登录游戏成功！");

        eventService.submitAsyncEvent(CreateRoleLoginEvent.valueOf(accountId));
    }

    @Override
    public void login(Channel channel, String account) {
        boolean checkAccount = accountService.checkAccount(account);
        if(!checkAccount){
            StringBuilder sb = new StringBuilder();
            sb.append("妲己：你还没有创建账号：").append(account).append("呢，快来创建帐号吧！");
            PacketUtils.send(channel, sb.toString());
            return ;
        }

        PlayerEntity player = playerService.getPlayer(account);
        player.login(channel);
        playerService.cacheChannelMap(channel, player);

        StringBuilder sb = new StringBuilder();
        sb.append("欢迎回来:").append(player.getAccountEntity().getNickName());
        PacketUtils.send(player, sb.toString());

        eventService.submitAsyncEvent(LoginEvent.valueOf(account));
    }

}