/**
 * FileName: ResourceService
 * Author:   坤龙
 * Date:     2020/4/7 21:37
 * Description: 配置表资源服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.service;

import com.frame.resource.IResource;
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
    public IResource getResource(Class<?> clz, int key) {
        return ResourceCacheHandler.getResource(clz, key);
    }
}