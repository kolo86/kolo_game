package com.game.scene.facade;

import com.frame.event.anno.EventAnno;
import com.frame.dispatcher.anno.ReceiverAnno;
import com.game.login.event.LoginEvent;
import com.game.role.event.CreateRoleSyncEvent;
import com.game.scene.service.ISceneService;
import com.game.signout.event.SignOutEvent;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br> 
 * 〈场景门面类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class SceneFacade {

    @Autowired
    private ISceneService sceneService;

    /**
     * 监听玩家创建角色事件，默认进入起始之地
     *
     */
    @EventAnno
    public void doCreateRole(CreateRoleSyncEvent event){
        sceneService.doCreateRole(event.getAccountId());
    }

    /**
     * 玩家登录事件
     *
     */
    @EventAnno
    public void doLogin(LoginEvent event){
        sceneService.login(event.getAccountId());
    }

    /**
     * 监听玩家退出游戏事件
     *
     */
    @EventAnno
    public void doSignOut(SignOutEvent event){
        sceneService.signOut(event.getAccountId());
    }

    /**
     * 玩家切换地图
     *
     */
    @ReceiverAnno
    public void changeMap(Channel channel, Message.Cm_ChangeMap change_map){
        sceneService.changeMap(channel, change_map.getMapId());
    }

    /**
     * 查看当前场景中的所有玩家状态
     *
     */
    @ReceiverAnno
    public void state(Channel channel, Message.Cm_State state){
        sceneService.state(channel);
    }

}