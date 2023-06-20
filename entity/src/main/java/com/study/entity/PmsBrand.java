package com.study.entity;

import com.study.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PmsBrand extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public PmsBrand(String name, String firstLetter, String logo, String description) {
        this.name = name;
        this.firstLetter = firstLetter;
        this.logo = logo;
        this.description = description;
    }

    public PmsBrand(String name, String firstLetter, String description) {
        this.name = name;
        this.firstLetter = firstLetter;
        this.description = description;
    }

    public PmsBrand(String id, Boolean active) {
        super(id);
        this.active = active;
    }

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 首字母
     */
    private String firstLetter;

    /**
     * 品牌logo
     */
    private String logo;

    /**
     * 品牌说明
     */
    private String description;

    /**
     * 状态
     */
    private Boolean active;

    private Long sort;


}
