package com.study.entity;

import com.study.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 图片表
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UmsImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * minio下的路径
     */
    private String path;

    private Long sort;

    public UmsImage(String md5, Long size, String contentType, String path) {
        this.md5 = md5;
        this.contentType = contentType;
        this.size = size;
        this.path = path;
    }
}
