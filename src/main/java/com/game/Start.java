package com.game;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.common.GlobalServiceImpl;
import com.game.common.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 游戏启动类
 * @author 坤龙
 */
public class Start {
	
	private static final Logger logger = LoggerFactory.getLogger(Start.class);
	
	private static final String DEFAULT_APPLICATION_CONTEXT = "applicationContext.xml";
	
	private static ClassPathXmlApplicationContext applicationContext;
	
	public static void main(String[] args) {
		// 读取Spring配置文件
		applicationContext = new ClassPathXmlApplicationContext(DEFAULT_APPLICATION_CONTEXT);
		
		// 定义钩子，在JVM即将被销毁的时候，先关闭其他程序
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if(applicationContext != null && applicationContext.isActive()) {
				GlobalServiceImpl service = applicationContext.getBean(GlobalServiceImpl.class);
				service.onStop();
			}
		}));

		// 初始化配置表数据
		ResourceCacheHandler.init();

		// 加载spring容器
		applicationContext.start();
		SpringContext.afterSpringStart(applicationContext);
		
		logger.info("spring初始化完成");
		
		// 通知所有模块，服务器启动
		GlobalServiceImpl service = applicationContext.getBean(GlobalServiceImpl.class);
		service.onStart();
		
		logger.info("其他模块初始化完成");
	}
	
}
