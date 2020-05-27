package com.seal.sharding.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.seal.sharding.common.entity.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author panliu
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 手机号
     */
    private String telPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户唯一ID
     */
    private String unionId;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 状态
     */
    private String status;


}
