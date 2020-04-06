package com.game.common;

import com.frame.event.impl.CloseServerSyncEvent;
import com.frame.event.impl.OpenServerSyncEvent;
import com.frame.event.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 全局服务实现类
 * @author 坤龙
 */
@Component
public class GlobalServiceImpl implements IGlobalService{

	@Autowired
	private IEventService eventService;

	public void onStop() {
		// 方法1 ： 直接调用各个模块的shutDown方法， 这样子会使得退出时，模块的关闭顺序得到控制
		// 方法2 ： 抛出shutDown同步事件最方便！
		eventService.submitSyncEvent(CloseServerSyncEvent.valueOf());
	}

	public void onStart() {
		// 方法1 ： 直接调用各个模块的start方法，这样子在启动时，模块的启动顺序得到控制
		// 方法2 ： 抛出onStart事件，这样子更为方便
		eventService.submitSyncEvent(OpenServerSyncEvent.valueOf());
	}
	
	
	
}
