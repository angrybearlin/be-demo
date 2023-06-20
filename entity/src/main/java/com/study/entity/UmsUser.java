package com.study.entity;

import com.study.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UmsUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public UmsUser(String name, String phone, String email, Integer gender, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public UmsUser(String id, Boolean active) {
        super(id);
        this.active = active;
    }

    public UmsUser(String id, String name, String phone, String email, Integer gender) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public UmsUser(String name, String phone, String email, Integer gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }


    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子信箱
     */
    private String email;

    /**
     * 1-男 0-女
     */
    private Integer gender;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 状态
     */
    private Boolean active;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 用户头像路径
     */
    private String icon;
}
