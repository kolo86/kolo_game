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
    public static final Map<String , Integer> NAME_ID_MAP = new HashMap<>();
    /** Map < NpcId , Npc名字 >*/
    public static final Map<Integer, String> ID_NAME_MAP = new HashMap<>();

    static{
        for(NpcEnum npc : values()){
            NAME_ID_MAP.put(npc.getName(), npc.getId());
            ID_NAME_MAP.put(npc.getId(), npc.getName());
        }
    }

    NpcEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 通过Npc名字获取NpcId
     *
     */
    public static Integer getNpcId(String npcName){
        return NAME_ID_MAP.get(npcName);
    }

    /**
     * 通过NpcId获取Npc名字
     *
     */
    public static String getNpcName(int npcId){
        return ID_NAME_MAP.get(npcId);
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