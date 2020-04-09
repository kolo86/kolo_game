/**
 * FileName: RoleEnum
 * Author:   坤龙
 * Date:     2020/4/2 14:51
 * Description: 角色类型
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.role.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色类型〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
public enum RoleEnum {
    /** 未建角色 */
    NONE(0,"未建角色"),
    /** 英雄 */
    HERO(1, "英雄"),
    /** 村民 */
    VILLAGER(2,"村民"),
    /** 怪物 */
    MONSTER(3,"怪物")

    ;

    private int roleId;

    private String roleName;

    private static final Map<Integer, String> idToNameMap = new HashMap<Integer, String>();

    private static final Map<String, Integer> nameToIdMap = new HashMap<String, Integer>();

    static{
        for(RoleEnum role : values()){
            idToNameMap.put(role.getRoleId(), role.getRoleName());
            nameToIdMap.put(role.getRoleName(), role.getRoleId());
        }
    }

    RoleEnum(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    /**
     * 通过角色ID获取角色名称
     *
     * @param roleId
     * @return
     */
    public static String getRoleNameById(int roleId){
        return idToNameMap.get(roleId);
    }

    /**
     * 通过角色名字得到角色ID
     *
     * @param roleName
     * @return
     */
    public static int getIdByRoleName(String roleName){
        return nameToIdMap.getOrDefault(roleName,0);
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}