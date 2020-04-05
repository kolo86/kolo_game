package com.game.common;

/**
 * 全局服务接口
 * @author 坤龙
 */
public interface IGlobalService {
	
	/**
	 * 通知所有模块服务端即将关闭
	 * 
	 */
	void onStop();
	
	/**
	 * 通知所有模块服务器启动
	 * 
	 */
	void onStart();
	
}
