package com.study.entity;

import com.study.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户和角色关联
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UmsRoleUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public UmsRoleUser(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户id
     */
    private String userId;

    private Long sort;


}
