package com.game.packback.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.item.model.AbstractItem;
import com.game.persistence.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈背包实体〉
 *
 * @author KOLO
 * @create 2020/4/10
 * @since 1.0.0
 */
@Data
@Entity(name = "backpack")
@Table(appliesTo = "backpack", comment = "背包")
public class BackPackEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int comment '背包ID'", nullable = false)
    private int id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID' ")
    private String accountId;

    @Column(columnDefinition = "mediumblob comment '背包数据'")
    private String packMapJson;

    /**
     * 背包缓存。Map< 道具唯一标识， 道具 ></>
     */
    private transient Map<Long, AbstractItem> packMap = new HashMap<>();

    @Override
    public void convert() {
        packMapJson = JSON.toJSONString(packMap);
    }

    @Override
    public void parse() {
        JSONObject jsonObject = JSON.parseObject(packMapJson);
        for( Object obj :  jsonObject.entrySet()){
            Map.Entry entry = (Map.Entry)obj;
            packMap.put((Long) entry.getKey(), (AbstractItem) entry.getValue());
        }
    }
}