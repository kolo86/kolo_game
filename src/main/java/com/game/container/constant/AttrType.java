package com.game.container.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈属性类型〉
 *
 * @author KOLO
 * @create 2020/4/8
 * @since 1.0.0
 */
public enum  AttrType {
    /** 最大血量 */
    MAX_HP(101, "MAX_HP", "最大血量"),
    /** 最大蓝量 */
    MAX_MP(102, "MAX_MP","最大蓝量"),
    /** 技能伤害比例，万分值 */
    SKILL_RATIO(103, "SKILL_RATIO","技能伤害比例，万分值"),
    /** 攻击力 */
    ATTACK(104, "ATTACK","攻击力"),
    ;

    /** 属性ID */
    private int attrId;
    /** 属性字符串 */
    private String attrStr;
    /** 属性名 */
    private String name;
    /** Map < 属性字符串， 属性类型 ></> */
    private static final Map<String, AttrType> NAME_TYPE_MAP = new HashMap<>();

    static{
        for(AttrType type : values()){
            NAME_TYPE_MAP.put(type.getAttrStr(), type);
        }
    }

    /**
     * 根据字符串得到属性类型
     *
     */
    public static AttrType getAttr(String str){
        return NAME_TYPE_MAP.get(str);
    }

    AttrType(int attrId, String attrStr, String name){
        this.attrId = attrId;
        this.attrStr = attrStr;
        this.name = name;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrStr() {
        return attrStr;
    }

    public void setAttrStr(String attrStr) {
        this.attrStr = attrStr;
    }
}