/**
 * FileName: SceneFacade
 * Author:   坤龙
 * Date:     2020/4/2 17:31
 * Description: 场景门面类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.scene.facade;

import com.frame.annotation.EventAnno;
import com.frame.annotation.ReceiverAnno;
import com.game.login.event.LoginEvent;
import com.game.role.event.CreateRoleEvent;
import com.game.scene.service.ISceneService;
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
     * @param event
     */
    @EventAnno
    public void doCreateRole(CreateRoleEvent event){
        sceneService.doCreateRole(event.getAccountId());
    }

    /**
     * 玩家登录事件
     *
     * @param event
     */
    @EventAnno
    public void doLogin(LoginEvent event){
        sceneService.login(event.getAccountId());
    }

    /**
     * 玩家切换地图
     *
     * @param channel
     * @param change_map
     */
    @ReceiverAnno
    public void changeMap(Channel channel, Message.ChangeMap change_map){
        sceneService.changeMap(channel, change_map.getMapId());
    }

    /**
     * 查看当前场景中的所有玩家状态
     *
     * @param channel
     * @param state
     */
    @ReceiverAnno
    public void state(Channel channel, Message.State state){
        sceneService.state(channel);
    }

}