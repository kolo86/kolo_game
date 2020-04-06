/**
 * FileName: PlayerFacade
 * Author:   坤龙
 * Date:     2020/4/3 10:55
 * Description: 玩家门面类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.account.facade;

import com.frame.annotation.EventAnno;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.account.service.IPlayerService;
import com.game.signout.event.SignOutEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家门面类〉
 *
 * @author 坤龙
 * @create 2020/4/3
 * @since 1.0.0
 */
@Component
public class PlayerFacade {

    @Autowired
    private IPlayerService playerService;

    /**
     * 监听起服事件
     *
     * @param event
     */
    @EventAnno
    public void onStart(OpenServerSyncEvent event){
        playerService.onStart();
    }

    /**
     * 监听玩家退出游戏事件
     *
     */
    @EventAnno
    public void doSignOut(SignOutEvent event){
        playerService.signOut(event.getAccountId());
    }
}