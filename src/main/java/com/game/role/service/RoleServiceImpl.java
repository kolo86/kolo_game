/**
 * FileName: RoleService
 * Author:   坤龙
 * Date:     2020/4/2 15:50
 * Description: 角色服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.role.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.persistence.IPersistenceService;
import com.game.role.constant.RoleEnum;
import com.game.role.entity.RoleEntity;
import com.game.role.event.CreateRoleEvent;
import com.game.util.PacketUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色服务类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IPersistenceService persistenceService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private RoleManager roleManager;

    @Override
    public void onStart() {
        if(roleManager.isInit()){
           return ;
        }

        List<Object> objectList = persistenceService.getAll(RoleEntity.class);
        for(Object obj : objectList){
            RoleEntity role = (RoleEntity) obj;
            roleManager.cacheRole(role);
        }
        roleManager.setInit(true);
    }

    @Override
    public void login(String accountId) {
        PlayerEntity player = playerService.getPlayer(accountId);
        if(player.checkRoleIsNull()){
            Channel channel = playerService.getChannel(player);
            PacketUtils.send(channel, "妲己：快来创建一个角色和我玩耍吧！");
        }
    }

    @Override
    public void doCreateRole(Channel channel, int roleType) {
        boolean checkCreateRoleCondition = checkCreateRoleCondition(channel, roleType);
        if(!checkCreateRoleCondition){
            return ;
        }
        PlayerEntity player = playerService.getPlayer(channel);

        RoleEntity entity = RoleEntity.valueOf(player.getAccountId(), roleType);
        persistenceService.save(entity);
        roleManager.cacheRole(entity);

        player.setRoleEntity(entity);
        player.setRoleType(roleType);
        persistenceService.update(player);

        StringBuilder sb = new StringBuilder();
        sb.append("恭喜你，创建角色：【").append(RoleEnum.getRoleNameById(roleType)).append("】成功！\n")
            .append("正在为你进入【起始之地】!请稍候！\n");
        PacketUtils.send(channel, sb.toString());

        eventService.submitAsyncEvent(CreateRoleEvent.valueOf(player.getAccountId()));
    }

    /**
     * 检查能够创建角色
     *
     * @param channel
     * @param roleType
     * @return
     */
    private boolean checkCreateRoleCondition(Channel channel, int roleType){
        PlayerEntity player = playerService.getPlayer(channel);
        if(player == null){
            PacketUtils.send(channel,"请你登录后，再进行操作！");
            return false;
        }

        int oldRoleType = player.getRoleType();
        if(oldRoleType != RoleEnum.NONE.getRoleId()){
            StringBuilder sb = new StringBuilder();
            sb.append("你已经有一个角色:【")
                    .append(RoleEnum.getRoleNameById(oldRoleType))
                    .append("】，不能再创建新角色！");
            PacketUtils.send(channel, sb.toString());
            return false;
        }
        return true;
    }

    @Override
    public RoleEntity getRoleByAccountId(String accountId) {
        if(!roleManager.isInit()){
            onStart();
        }

        return roleManager.getRole(accountId);
    }
}