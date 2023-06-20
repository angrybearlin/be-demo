package com.study.entity;

import com.study.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UmsRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public UmsRole(String id, String name) {
        super(id);
        this.name = name;
    }

    public UmsRole(String name) {
        this.name = name;
    }

    public UmsRole(String id, Boolean active) {
        super(id);
        this.active = active;
    }

    /**
     * 角色
     */
    private String name;

    /**
     * 状态
     */
    private Boolean active;

    private Long sort;

}
