package com.game.persistence;

import java.util.List;

/**
 *
 * @author 坤龙
 */
public interface IPersistenceService {

    /**
     * 存储对象
     *
     * @param entity
     */
    void save(Object entity);

    /**
     * 更新对象
     *
     * @param entity
     */
    void update(Object entity);

    /**
     * 查询所有数据
     *
     * @param clz
     * @return
     */
    List<Object> getAll(Class<?> clz);
}
