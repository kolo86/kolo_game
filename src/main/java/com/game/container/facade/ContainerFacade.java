package com.game.container.facade;

import com.frame.event.anno.EventAnno;
import com.game.container.service.IContainerService;
import com.game.login.event.LoginEvent;
import com.game.role.event.CreateRoleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈容器门面类〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
@Component
public class ContainerFacade {

    @Autowired
    private IContainerService containerService;

    /**
     * 监听玩家创建角色事件
     *
     * @param event
     */
    @EventAnno
    public void doCreateRole(CreateRoleEvent event){
        containerService.initPlayerContainer(event.getAccountId());
    }

    /**
     * 监听玩家登录事件
     *
     */
    @EventAnno
    public void doPlayerLogin(LoginEvent event){
        containerService.initRecoverCommand(event.getAccountId());
    }

}