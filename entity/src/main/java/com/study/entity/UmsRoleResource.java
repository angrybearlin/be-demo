package com.study.entity;

import com.study.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色关联权限
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UmsRoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public UmsRoleResource(String roleId, String resourceId, Integer resourceType) {
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 资源类别
     */
    private Integer resourceType;

    private Long sort;


}
