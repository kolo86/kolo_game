/**
 * FileName: FieldEnum
 * Author:   坤龙
 * Date:     2020/4/7 21:06
 * Description: 字段类型枚举
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.frame.resource.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈字段类型枚举〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */

import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型
 * @author kolo
 */
public enum FieldEnum {

    STRING("String") {
        @Override
        public Object parseData(XSSFCell rowCell) {
            return rowCell.getStringCellValue();
        }
    },
    INT("int") {
        @Override
        public Object parseData(XSSFCell rowCell) {
            return Double.valueOf(rowCell.getNumericCellValue()).intValue();
        }
    },

    ;

    /**
     * 字段值
     */
    private String name;
    /**
     * Map< 字段名 ， 字段类型 >
     */
    private static final Map<String, FieldEnum> fieldMap = new HashMap<String, FieldEnum>();

    static {
        for (FieldEnum type : values()) {
            fieldMap.put(type.getName().toUpperCase(), type);
        }
    }

    FieldEnum(String name) {
        this.name = name;
    }

    /**
     * 根据字段值获取字段类型
     *
     * @param name
     * @return
     */
    public static FieldEnum getType(String name) {
        return fieldMap.get(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Object parseData(XSSFCell rowCell);
}