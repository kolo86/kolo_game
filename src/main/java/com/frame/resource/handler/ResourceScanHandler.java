/**
 * FileName: ResourceScanHandler
 * Author:   坤龙
 * Date:     2020/4/7 21:41
 * Description: 资源扫描处理器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.handler;

import com.frame.resource.anno.Resource;
import com.frame.resource.constant.ResourceConstant;
import lombok.Data;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br> 
 * 〈资源扫描处理器〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Data
public class ResourceScanHandler {

    /** Map< 资源名， 资源Class路径 ></> */
    private static final Map<String, Class<?>> nameClassMap = new HashMap<String, Class<?>>();

    /**
     * 初始化资源扫描处理器
     *
     */
    public static void init(){
        String basePath = ResourceConstant.SCAN_PACKET;
        Reflections reflections = new Reflections(basePath);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Resource.class);
        for(Class<?> clz : classSet){
            nameClassMap.put(clz.getSimpleName().toUpperCase(), clz);
        }
    }

    /**
     * 获取资源Class路径
     *
     * @param name
     * @return
     */
    public static Class<?> getResourceClass(String name){
        return nameClassMap.get(name);
    }

}