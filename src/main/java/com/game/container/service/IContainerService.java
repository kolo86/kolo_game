package com.game.container.service;

/**
 * 〈一句话功能简述〉<br>
 * 〈容器接口〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public interface IContainerService {

    /**
     * 初始化玩家容器
     *
     * @param accountId
     */
    void initPlayerContainer(String accountId);

    /**
     * 执行定时恢复命令
     *
     * @param accountId
     */
    void initRecoverCommand(String accountId);
}