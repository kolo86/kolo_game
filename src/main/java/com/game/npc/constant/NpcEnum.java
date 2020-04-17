/**
 * FileName: NpcEnum
 * Author:   坤龙
 * Date:     2020/4/7 14:29
 * Description: Npc枚举类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.npc.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Npc枚举类〉
 *
 * @author 坤龙
 * @create 2020/4/7
 * @since 1.0.0
 */
public enum NpcEnum {
    /** 村民NPC */
    VILLAGER(101 , "村民NPC")

    ;

    /** NpcId */
    private int id;
    /** Npc名字 */
    private String name;
    /** Map < Npc名字 ， NpcId >  */
    public static final Map<String , Integer> nameIdMap = new HashMap<String, Integer>();
    /** Map < NpcId , Npc名字 >*/
    public static final Map<Integer, String> idNameMap = new HashMap<Integer, String>();

    static{
        for(NpcEnum npc : values()){
            nameIdMap.put(npc.getName(), npc.getId());
            idNameMap.put(npc.getId(), npc.getName());
        }
    }

    NpcEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 通过Npc名字获取NpcId
     *
     * @param npcName
     * @return
     */
    public static Integer getNpcId(String npcName){
        return nameIdMap.get(npcName);
    }

    /**
     * 通过NpcId获取Npc名字
     *
     * @param npcId
     * @return
     */
    public static String getNpcName(int npcId){
        return idNameMap.get(npcId);
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