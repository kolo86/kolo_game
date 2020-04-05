/**
 * FileName: ISceneService
 * Author:   坤龙
 * Date:     2020/4/2 17:41
 * Description: 场景接口类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.scene.service;


import io.netty.channel.Channel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈场景接口类〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public interface ISceneService {

    /**
     * 当玩家创建完角色后，进入地图
     *
     * @param account
     */
    void doCreateRole(String account);

    /**
     * 玩家登录
     *
     * @param accountId
     */
    void login(String accountId);

    /**
     * 切换地图
     *
     * @param channel
     * @param mapId
     */
    void changeMap(Channel channel, int mapId);

    /**
     * 查看当前场景中的所有玩家状态
     *
     * @param channel
     */
    void state(Channel channel);
}