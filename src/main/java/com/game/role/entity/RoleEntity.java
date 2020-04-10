package com.game.role.entity;

import com.game.persistence.AbstractEntity;
import com.game.role.constant.RoleStateEnum;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈角色实体〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
@Entity(name = "role")
@Table(appliesTo = "role", comment = "角色")
public class RoleEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(columnDefinition = "int comment '角色ID'", nullable = false)
    private int id;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID'")
    private String accountId;

    @Column(columnDefinition = "int comment '角色类型'")
    private int roleType;

    @Column(columnDefinition = "int comment '角色状态'")
    private int roleState;

    public static RoleEntity valueOf(String accountId, int roleType){
        RoleEntity entity = new RoleEntity();
        entity.accountId = accountId;
        entity.roleType = roleType;
        entity.roleState = RoleStateEnum.EXIST.getId();
        return entity;
    }
}