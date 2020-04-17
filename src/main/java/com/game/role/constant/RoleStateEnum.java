package com.game.role.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色状态枚举〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public enum RoleStateEnum {
    /** 生存 */
    EXIST(1, "生存"),
    /** 死亡 */
    DEAD(2,"死亡")

    ;

    private int id;

    private String name;

    /** MAP < 标识ID， 状态 > */
    private static final Map<Integer, String> ID_NAME_MAP = new HashMap<>();

    static{
        for(RoleStateEnum stateEnum : values()){
            ID_NAME_MAP.put(stateEnum.getId(), stateEnum.getName());
        }
    }

    RoleStateEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 通过标识ID获取状态
     *
     */
    public static String getStateNameById(int id){
        return ID_NAME_MAP.get(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}