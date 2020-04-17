package com.frame.resource.constant;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型
 * @author kolo
 */
public enum FieldEnum {
    /** 字符串类型 */
    STRING("String") {
        @Override
        public Object parseData(XSSFCell rowCell) {
            rowCell.setCellType(CellType.STRING);
            return rowCell.getStringCellValue();
        }
    },
    /** 整型 */
    INT("int") {
        @Override
        public Object parseData(XSSFCell rowCell) {
            return Double.valueOf(rowCell.getNumericCellValue()).intValue();
        }
    },
    /** 长整型 */
    Long("long"){
        @Override
        public Object parseData(XSSFCell rowCell) {
            return Double.valueOf(rowCell.getNumericCellValue()).longValue();
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
    private static final Map<String, FieldEnum> FIELD_MAP = new HashMap<>();

    static {
        for (FieldEnum type : values()) {
            FIELD_MAP.put(type.getName().toUpperCase(), type);
        }
    }

    FieldEnum(String name) {
        this.name = name;
    }

    /**
     * 根据字段值获取字段类型
     *
     */
    public static FieldEnum getType(String name) {
        return FIELD_MAP.get(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Object parseData(XSSFCell rowCell);
}