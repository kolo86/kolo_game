/**
 * FileName: PlayerServiceImpl
 * Author:   坤龙
 * Date:     2020/4/2 15:05
 * Description: 玩家实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.account.service;

import com.game.account.entity.AccountEntity;
import com.game.account.entity.PlayerEntity;
import com.game.packback.entity.BackPackEntity;
import com.game.packback.service.IBackPackService;
import com.game.persistence.service.IPersistenceService;
import com.game.role.entity.RoleEntity;
import com.game.role.service.IRoleService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家实现类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Component
public class PlayerServiceImpl implements IPlayerService {

    @Autowired
    private PlayerManager playerManager;
    @Autowired
    private IPersistenceService persistenceService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IBackPackService backPackService;

    @Override
    public void onStart() {
        List<Object> objectList = persistenceService.getAll(PlayerEntity.class);
        for(Object obj : objectList){
            PlayerEntity player =  (PlayerEntity)obj;
            AccountEntity accountEntity = accountService.getEntityByAccountId(player.getAccountId());
            player.setAccountEntity(accountEntity);

            RoleEntity roleEntity = roleService.getRoleByAccountId(player.getAccountId());
            player.setRoleEntity(roleEntity);

            BackPackEntity backpack = backPackService.getBackpack(player.getAccountId());
            player.setBackPackEntity(backpack);
            playerManager.cachePlayerMap(player);
        }
    }

    @Override
    public void save(PlayerEntity playerEntity) {
        persistenceService.save(playerEntity);
        playerManager.cachePlayerInfo(playerEntity);
    }

    @Override
    public PlayerEntity getPlayer(Channel channel) {
        return playerManager.getPlayerByChannel(channel);
    }

    @Override
    public PlayerEntity getPlayer(String accountId) {
        return playerManager.getPlayerByAccountId(accountId);
    }

    @Override
    public Channel getChannel(PlayerEntity playerEntity) {
        return playerManager.getChannelByPlayer(playerEntity);
    }

    @Override
    public void cacheChannelMap(Channel channel, PlayerEntity player) {
        playerManager.cacheChannelMap(channel, player);
    }

    @Override
    public void signOut(String accountId) {
        playerManager.removeChannel(accountId);
    }

    @Override
    public void closeService() {
        playerManager.closeService();
    }
}