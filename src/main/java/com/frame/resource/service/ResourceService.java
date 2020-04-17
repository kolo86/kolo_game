package com.frame.resource.service;

import com.frame.resource.AbstractResource;
import com.frame.resource.handler.ResourceCacheHandler;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈配置表资源服务类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Component
public class ResourceService implements IResourceService {

    @Override
    public AbstractResource getResource(Class<?> clz, int key) {
        return ResourceCacheHandler.getResource(clz, key);
    }
}