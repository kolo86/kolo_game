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
    private static final Map<String, Class<?>> NAME_CLASS_MAP = new HashMap<>();

    /**
     * 初始化资源扫描处理器
     *
     */
    public static void init(){
        String basePath = ResourceConstant.SCAN_PACKET;
        Reflections reflections = new Reflections(basePath);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Resource.class);
        for(Class<?> clz : classSet){
            NAME_CLASS_MAP.put(clz.getSimpleName().toUpperCase(), clz);
        }
    }

    /**
     * 获取资源Class路径
     *
     */
    public static Class<?> getResourceClass(String name){
        return NAME_CLASS_MAP.get(name);
    }

}