package com.game.account.entity;

import com.frame.resource.handler.ResourceCacheHandler;
import com.game.account.resource.PlayerResource;
import com.game.container.AbstractContainer;
import com.game.container.constant.ContainerType;
import com.game.role.constant.RoleEnum;
import com.game.role.entity.RoleEntity;
import com.game.scene.constant.SceneType;
import io.netty.channel.Channel;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br> 
 * 〈玩家信息〉
 *
 * @author 坤龙
 * @create 2020/4/2
 * @since 1.0.0
 */
@Data
@Entity(name = "player")
@Table(appliesTo = "player", comment = "玩家信息")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition = "int comment '角色ID'",  nullable = false)
    private int playerId;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID'", nullable = false)
    private String accountId;

    @Column(columnDefinition = "int comment '角色类型'")
    private int roleType;

    @Column(columnDefinition = "int comment '地图ID'")
    private int mapId;

    /** 是否在线 */
    private transient boolean online = false;

    /** 角色信息 */
    private transient RoleEntity roleEntity;

    /** 账号信息 */
    private transient AccountEntity accountEntity;

    /** 通道信息 */
    private transient Channel channel;

    /** 角色的属性容器 ，这里暂不存进数据库中*/
    private transient Map<ContainerType, AbstractContainer> containerMap = new ConcurrentHashMap<>();

    /**
     * 创建角色实体
     *
     * @param accountEntity
     * @return
     */
    public static PlayerEntity valueOf(AccountEntity accountEntity){
        PlayerEntity playerEnt = new PlayerEntity();
        playerEnt.accountId = accountEntity.getAccountId();
        playerEnt.roleType = RoleEnum.NONE.getRoleId();
        playerEnt.mapId = SceneType.NONE.getMapId();
        playerEnt.accountEntity = accountEntity;
        return playerEnt;
    }

    /**
     * 玩家登录上线
     *
     */
    public void online(){
        this.online = true;
    }

    /**
     * 玩家是否还没创建角色
     *
     */
    public boolean checkRoleIsNull(){
        return this.roleType == RoleEnum.NONE.getRoleId();
    }

    /**
     * 玩家登录时，保存玩家通道信息
     *
     * @param channel
     */
    public void login(Channel channel){
        this.channel = channel;
        this.online = true;
        // 初始化玩家容器
        initContainer();
    }

    /**
     * 玩家退出游戏
     *
     */
    public void signOut(){
        this.online = false;
    }

    /**
     * 初始化玩家容器
     *
     */
    public void initContainer(){
        if(this.roleType == RoleEnum.NONE.getRoleId()){
            return ;
        }

        PlayerResource resource = (PlayerResource) ResourceCacheHandler.getResource(PlayerResource.class, this.roleType);
        this.containerMap = ContainerType.initPlayerContainer(resource);
    }
}