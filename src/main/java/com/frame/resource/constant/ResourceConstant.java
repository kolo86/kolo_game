package com.frame.resource.constant;

import org.springframework.util.ResourceUtils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈资源常量〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public interface ResourceConstant {

    /** 扫描包的范围 */
    String SCAN_PACKET = "com.game";
    /** 配置表目录 */
    String RESOURCE_PACKET = ResourceUtils.CLASSPATH_URL_PREFIX + "resource";
}