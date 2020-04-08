/**
 * FileName: ResourceHandler
 * Author:   坤龙
 * Date:     2020/4/7 21:16
 * Description: 资源处理器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.handler;

import com.frame.resource.IResource;
import com.frame.resource.constant.ResourceConstant;
import com.frame.resource.util.ResourceParseUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br> 
 * 〈资源缓存器〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public class ResourceCacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCacheHandler.class);

    /** Map< 该配置文件的Class, Map< 配置的主键ID， 配置文件 > > */
    public static final Map<Class<?>, Map<Integer, IResource>> resourceMap = new HashMap<Class<?>, Map<Integer, IResource>>();

    /**
     * 初始化资源缓存器
     *
     */
    public static void init(){
        ResourceScanHandler.init();
        initCache();
        logger.info("配置表加载完成！");
    }

    /**
     * 初始化资源缓存信息
     *
     */
    private static void initCache(){
        try{
            File file = ResourceUtils.getFile(ResourceConstant.RESOURCE_PACKET);
            File[] listFiles = file.listFiles();
            for (File fileTemp : listFiles) {
                FileInputStream fileInputStream = new FileInputStream(fileTemp);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                ResourceParseUtils.parseWorkBook(workbook);
            }
        }catch (Exception e){
            logger.error("初始化配置表出错！");
            e.printStackTrace();
        }
    }

    /**
     * 获取配置文件
     *
     * @param clz
     * @param key
     * @return
     */
    public static IResource getResource(Class<?> clz, Integer key){
        Map<Integer, IResource> objectMap = resourceMap.get(clz);
        if(Objects.isNull(objectMap)){
            logger.error("当配置文件class为：{}时，无法获取对应的配置文件列表", clz);
            return null;
        }

        IResource obj = objectMap.get(key);
        if(ObjectUtils.isEmpty(obj)){
            logger.error("当配置文件class为：{},主键为：{}时，无法获取对应的配置文件列表", clz, key);
            return null;
        }
        return obj;
    }

    /**
     * 缓存配置表
     *
     * @param clz
     * @param obj
     */
    public static void addResource(Class<?> clz, IResource obj){
        Map<Integer, IResource> resourceMapTemp = resourceMap.get(clz);
        if(ObjectUtils.isEmpty(resourceMapTemp)){
            resourceMapTemp = new HashMap<Integer, IResource>();
            resourceMapTemp.put(obj.getResourceId(), obj);
            resourceMap.put(clz, resourceMapTemp);
            return ;
        }
        resourceMapTemp.put(obj.getResourceId(), obj);
    }

}