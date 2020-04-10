package com.game.account.entity;

import com.game.persistence.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 账号实体
 * @author 坤龙
 */
@Data
@Entity(name = "account")
@Table(appliesTo = "account", comment = "账号信息")
public class AccountEntity extends AbstractEntity {
	
	@Id
	@Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号ID'", nullable = false)
	private String accountId;
	
	@Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号密码'", nullable = false)
	private String nickName;
	
	@Column(columnDefinition = "timestamp comment '创建时间'")
	private Date createTime;

	public static AccountEntity valueOf(String accountId, String nickName) {
		AccountEntity entity = new AccountEntity();
		entity.accountId = accountId;
		entity.nickName = nickName;
		entity.createTime = new Date();
		return entity;
	}
	
}
