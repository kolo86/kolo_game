/**
 * FileName: IResourceService
 * Author:   坤龙
 * Date:     2020/4/7 21:35
 * Description: 资源接口类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.service;

import com.frame.resource.IResource;

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
     * @param clz
     * @param key
     * @return
     */
    IResource getResource(Class<?> clz, int key);

}