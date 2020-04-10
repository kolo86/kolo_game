package com.game.persistence.service;

import com.game.persistence.AbstractEntity;

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
    void save(AbstractEntity entity);

    /**
     * 更新对象
     *
     * @param entity
     */
    void update(AbstractEntity entity);

    /**
     * 查询所有数据
     *
     * @param clz
     * @return
     */
    List<Object> getAll(Class<?> clz);
}
