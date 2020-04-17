package com.game.role.facade;

import com.frame.event.anno.EventAnno;
import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.event.impl.CloseServerSyncEvent;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.account.service.IPlayerService;
import com.game.login.event.CreateRoleLoginEvent;
import com.game.role.service.IRoleService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br> 
 * 〈角色门面类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class RoleFacade {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPlayerService playerService;

    /**
     * 创建角色
     *
     */
    @ReceiverAnno
    public void doCreateRole(Channel channel, Message.Cm_CreateRole message){
        roleService.doCreateRole(channel, message.getRoleType());
    }

    /**
     * 监听玩家登录事件
     *
     */
    @EventAnno
    public void doPlayerLogin(CreateRoleLoginEvent event){
        roleService.login(event.getAccountId());
    }

    /**
     * 监听起服事件，初始化角色信息
     *
     */
    @EventAnno
    public void onStart(OpenServerSyncEvent event){
        roleService.onStart();
    }

    /**
     * 关服
     *
     */
    @EventAnno
    public void closeService(CloseServerSyncEvent event){
        roleService.closeService();
    }
}