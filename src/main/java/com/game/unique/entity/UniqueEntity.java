package com.game.unique.entity;

import com.game.persistence.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 〈一句话功能简述〉<br>
 * 〈唯一性实体〉
 *
 * @author KOLO
 * @create 2020/4/13
 * @since 1.0.0
 */
@Data
@Entity(name = "uniquement")
@Table(appliesTo = "uniquement", comment = "唯一性")
public class UniqueEntity extends AbstractEntity {

    @Id
    @Column(columnDefinition = "int comment '类型ID'", nullable = false)
    private int id;

    @Column(columnDefinition = "int comment '当前值'")
    private int currentValue;

    public static UniqueEntity valueOf(int id, int currentValue){
        UniqueEntity entity = new UniqueEntity();
        entity.id = id;
        entity.currentValue = currentValue;
        return entity;
    }
}