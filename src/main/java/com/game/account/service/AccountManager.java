package com.game.account.service;


import com.game.account.entity.AccountEntity;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
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

	// 是否已经被初始化
	private transient boolean init = false;

	private Map<String, AccountEntity> accountMap = new ConcurrentHashMap<String, AccountEntity>();
	
	/**
	 * 缓存账号
	 * 
	 */
	public void addAccount(AccountEntity entity) {
		accountMap.put(entity.getAccountId(), entity);
	}
	
	/**
	 * 获取所有已存在的账号ID
	 * 
	 * @return
	 */
	public Set<String> getAllAccountId(){
		return accountMap.keySet();
	}
	
	/**
	 * 检查当前账号是否已存在
	 * 
	 * @param accountId
	 * @return
	 */
	public boolean checkAccount(String accountId) {
		return accountMap.get(accountId) != null;
	}

}
