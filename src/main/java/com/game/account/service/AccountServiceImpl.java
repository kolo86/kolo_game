package com.game.account.service;

import com.frame.event.service.IEventService;
import com.game.account.entity.AccountEntity;
import com.game.account.entity.PlayerEntity;
import com.game.account.event.CreateAccountAsyncEvent;
import com.game.common.constant.I18nId;
import com.game.persistence.service.IPersistenceService;
import com.game.util.MD5Utils;
import com.game.util.PacketUtils;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 账号服务类
 * 
 * @author KOLO
 *
 */
@Component
public class AccountServiceImpl implements IAccountService{
	
	private static final Logger logger = LoggerFactory.getLogger("AccountService");
	
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private IPersistenceService persistenceService;
	@Autowired
	private IEventService eventService;
	@Autowired
	private IPlayerService playerService;

	/**
	 * 起服缓存数据库中的账号数据
	 *
	 */
	@Override
	public void onStart(){
		if(accountManager.isInit()){
			return ;
		}

		List<Object> allAccountEntity = persistenceService.getAll(AccountEntity.class);
		for(Object obj : allAccountEntity){
			AccountEntity entity = (AccountEntity)obj;
			accountManager.addAccount(entity);
		}
		accountManager.setInit(true);
	}

	@Override
	public boolean checkAccount(String account) {
		return accountManager.checkAccount(account);
	}

	/**
	 * 保存新账号
	 * 
	 */
	@Override
	public void saveAccount(Channel channel, AccountEntity entity) {
		if(accountManager.checkAccount(entity.getAccountId())) {
			PacketUtils.sendResponse(channel, I18nId.ACCOUNT_HAS_BEEN_REGISTERED);
		}
		
		accountManager.addAccount(entity);
		persistenceService.save(entity);
	}

	/**
	 * 创建新账号
	 *
	 */
	@Override
	public void createAccount(Channel channel, Message.Cm_Register message) {
		String accountId = MD5Utils.encryption(message.getAccount());
		if(checkAccount(accountId)){
			PacketUtils.sendResponse(channel, I18nId.ACCOUNT_HAS_BEEN_REGISTERED);
			return ;
		}

		AccountEntity newAccount = AccountEntity.valueOf(accountId, message.getNickName());
		saveAccount(channel, newAccount);

		PlayerEntity playerEntity = PlayerEntity.valueOf(newAccount);
		playerEntity.setChannel(channel);
		playerService.save(playerEntity);

		PacketUtils.sendResponse(channel, I18nId.REGISTER_WAS_SUCCESSFUL);
		eventService.submitAsyncEvent(CreateAccountAsyncEvent.valueOf(newAccount.getAccountId()));

		logger.info("玩家注册：账号{},昵称：{}", message.getAccount(), message.getNickName() );
	}

	@Override
	public AccountEntity getEntityByAccountId(String accountId) {
		if(!accountManager.isInit()){
			onStart();
		}

		return accountManager.getAccountMap().get(accountId);
	}

	@Override
	public void closeServer() {
		accountManager.closeServer();
	}
}
