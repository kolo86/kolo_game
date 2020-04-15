package com.game.unique.service;

import com.game.unique.constant.UniqueType;

/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性服务类接口〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public interface IUniqueService {

    /**
     * 获取唯一性ID
     *
     * @param type
     * @return
     */
    int getUniqueId(UniqueType type);

    /**
     * 处理开服的事情
     *
     */
    void doOpenServer();
}