/**
 * FileName: RoleStateEnum
 * Author:   坤龙
 * Date:     2020/4/2 17:08
 * Description: 角色状态枚举
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
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

    // MAP < 标识ID， 状态 >
    private static final Map<Integer, String> idNameMap = new HashMap<Integer, String>();

    static{
        for(RoleStateEnum stateEnum : values()){
            idNameMap.put(stateEnum.getId(), stateEnum.getName());
        }
    }

    RoleStateEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 通过标识ID获取状态
     *
     * @param id
     * @return
     */
    public static String getStateNameById(int id){
        return idNameMap.get(id);
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