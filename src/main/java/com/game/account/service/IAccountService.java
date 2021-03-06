package com.game.account.service;

import com.game.account.entity.AccountEntity;
import com.netty.proto.Message;
import io.netty.channel.Channel;

/**
 * 账号服务接口
 * @author 坤龙
 */
public interface IAccountService {

	/**
	 * 创建新账号
	 *
	 */
	void createAccount(Channel channel, Message.Cm_Register message);

	/**
	 * 检查账号是否已存在
	 *
	 */
	boolean checkAccount(String account);

	/**
	 * 保存新账号
	 * 
	 */
	void saveAccount(Channel channel, AccountEntity entity);

	/**
	 * 服务器启动时
	 *
	 */
	void onStart();

	/**
	 * 通过账号ID获取实体信息
	 *
	 */
	AccountEntity getEntityByAccountId(String accountId);

	/**
	 * 关服
	 *
	 */
	void closeServer();
}
