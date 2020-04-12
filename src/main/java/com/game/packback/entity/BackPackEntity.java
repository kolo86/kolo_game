package com.game.packback.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.game.item.AbstractItem;
import com.game.item.model.MedicineItem;
import com.game.persistence.AbstractEntity;
import com.game.persistence.util.ParseUtils;
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
        packMap = ParseUtils.parseToMap(packMapJson, Long.class, AbstractItem.class);
    }

    public static BackPackEntity valueOf(String accountId){
        BackPackEntity backPack = new BackPackEntity();
        backPack.accountId = accountId;
        return backPack;
    }


}