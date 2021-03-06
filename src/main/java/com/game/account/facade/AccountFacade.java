package com.game.account.facade;

import com.frame.event.anno.EventAnno;
import com.frame.dispatcher.anno.ReceiverAnno;
import com.frame.event.impl.CloseServerSyncEvent;
import com.frame.event.impl.OpenServerSyncEvent;
import com.game.account.service.IAccountService;
import com.netty.proto.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账号门面类
 * @author 坤龙
 */
@Component
public class AccountFacade {

	private static final Logger logger = LoggerFactory.getLogger(AccountFacade.class);

	@Autowired
	private IAccountService accountService;

	/**
	 * 创建账号
	 */
	@ReceiverAnno
	public void createAccount(Channel channel, Message.Cm_Register message) {
		accountService.createAccount(channel, message);
	}

	/**
	 * 监听开服事件，缓存账号信息
	 *
	 */
	@EventAnno(level = 100)
	public void doOpenServer(OpenServerSyncEvent event){
		accountService.onStart();
	}

	/**
	 * 监听关服事件
	 *
	 */
	@EventAnno
	public void doCloseServer(CloseServerSyncEvent event){
		accountService.closeServer();
	}
}
