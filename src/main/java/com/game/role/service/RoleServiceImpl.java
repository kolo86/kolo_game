package com.game.role.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.PlayerEntity;
import com.game.account.service.IPlayerService;
import com.game.common.constant.I18nId;
import com.game.persistence.service.IPersistenceService;
import com.game.role.constant.RoleEnum;
import com.game.role.entity.RoleEntity;
import com.game.role.event.CreateRoleSyncEvent;
import com.game.util.PacketUtils;
import com.netty.common.ProtocolEnum;
import com.netty.proto.Message;
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
            PacketUtils.sendResponse(channel, I18nId.PLEASE_CREATE_A_ROLE);
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

        Message.Sm_CreateRole smCreateRole = Message.Sm_CreateRole.newBuilder().setSuccess(true).build();
        PacketUtils.send(player, ProtocolEnum.Sm_CreateRole.getId() , smCreateRole.toByteArray());

        eventService.submitAsyncEvent(CreateRoleSyncEvent.valueOf(player.getAccountId()));
    }

    /**
     * 检查能够创建角色
     *
     */
    private boolean checkCreateRoleCondition(Channel channel, int roleType){
        PlayerEntity player = playerService.getPlayer(channel);
        if(player == null){
            PacketUtils.sendResponse(channel,I18nId.PLEASE_LOGIN);
            return false;
        }

        int oldRoleType = player.getRoleType();
        if(oldRoleType != RoleEnum.NONE.getRoleId()){
            PacketUtils.sendResponse(channel, I18nId.CANNOT_CREATE_ROLE);
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

    @Override
    public void closeService() {
        roleManager.closeService();
    }
}