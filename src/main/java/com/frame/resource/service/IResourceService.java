package com.frame.resource.service;

import com.frame.resource.AbstractResource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈资源接口类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public interface IResourceService {

    /**
     * 获取配置表资源
     *
     */
    AbstractResource getResource(Class<?> clz, int key);

}