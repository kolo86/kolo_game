package com.game.login.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IAccountService;
import com.game.account.service.IPlayerService;
import com.game.common.constant.I18nId;
import com.game.login.event.CreateRoleLoginEvent;
import com.game.login.event.LoginEvent;
import com.game.util.MD5Utils;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;
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
            logger.error("玩家创建角色后，登录失败，无法根据账号ID获取账号信息");
        }

        player.online();
        PacketUtils.sendResponse(player, I18nId.LOGIN_IN_SUCCESSFULLY);

        eventService.submitAsyncEvent(CreateRoleLoginEvent.valueOf(accountId));
    }

    @Override
    public void login(Channel channel, String account) {
        String encryptionAccount = MD5Utils.encryption(account);
        boolean checkAccount = accountService.checkAccount(encryptionAccount);
        if(!checkAccount){
            PacketUtils.sendResponse(channel, I18nId.THE_ACCOUNT_HAS_NOT_BEEN_CREATED);
            return ;
        }

        PlayerEntity player = playerService.getPlayer(encryptionAccount);
        player.login(channel);
        playerService.cacheChannelMap(channel, player);

        Message.Sm_Login smLogin = Message.Sm_Login.newBuilder().setSuccess(true).build();
        PacketUtils.send(player, ProtocolEnum.SM_LOGIN.getId(),smLogin.toByteArray());

        eventService.submitAsyncEvent(LoginEvent.valueOf(encryptionAccount));
    }

}