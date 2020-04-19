package com.game.account.service;


import com.game.account.entity.AccountEntity;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 账号管理者
 * 
 * @author KOLO
 *
 */
@Data
@Component
public class AccountManager{
	
	private static final Logger logger = LoggerFactory.getLogger("AccountManager");

	private Map<String, AccountEntity> accountMap = new ConcurrentHashMap<>();
	
	/**
	 * 缓存账号
	 * 
	 */
	public void addAccount(AccountEntity entity) {
		accountMap.put(entity.getAccountId(), entity);
	}

	/**
	 * 检查当前账号是否已存在
	 * 
	 */
	public boolean checkAccount(String accountId) {
		return accountMap.get(accountId) != null;
	}

	/**
	 * 关服时执行的操作
	 *
	 */
	public void closeServer(){
		accountMap.clear();
	}
}
