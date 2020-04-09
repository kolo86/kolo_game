/**
 * FileName: AbstractResource
 * Author:   坤龙
 * Date:     2020/4/7 21:26
 * Description: 抽象资源类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈资源接口类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public abstract class AbstractResource {

    /**
     * 获取配置表的ID
     *
     * @return 配置表ID
     * */
    public abstract int getResourceId();

    /**
     * 当该配置加载完成，执行的初始化操作
     */
    public void init() {
        //需要初始化的配置表才去重写
    }
}