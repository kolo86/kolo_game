package com.game.item.constant;

/**
 * 〈一句话功能简述〉<br>
 * 〈道具类型〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
public enum ItemType {


    ;
    /** 道具类型ID */
    private int id;
    /** 道具类型名称 */
    private String name;

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