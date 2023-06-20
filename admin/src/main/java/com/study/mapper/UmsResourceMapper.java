package com.study.mapper;

import com.study.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-14
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    List<UmsResource> getByUserId(String userId);
}
