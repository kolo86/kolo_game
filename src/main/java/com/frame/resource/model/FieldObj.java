/**
 * FileName: FieldObj
 * Author:   坤龙
 * Date:     2020/4/7 21:04
 * Description: 单个字段的信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.model;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br> 
 * 〈单个字段的信息〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
@Data
public class FieldObj {
    /** 列数 */
    private int index;
    /** 字段名 */
    private String fieldName;
    /** 类型 */
    private String type;

    public static FieldObj valueOf(int index, String fieldName, String type) {
        FieldObj obj = new FieldObj();
        obj.index = index;
        obj.fieldName = fieldName;
        obj.type = type;
        return obj;
    }

}