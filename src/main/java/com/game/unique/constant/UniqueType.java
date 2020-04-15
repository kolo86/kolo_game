package com.game.unique.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性的类型〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
public enum  UniqueType {

    /** 道具 */
    ITEM(101, "道具"),

    ;

    private int id;

    private String name;

    UniqueType(int id, String name){
        this.id = id;
        this.name = name;
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