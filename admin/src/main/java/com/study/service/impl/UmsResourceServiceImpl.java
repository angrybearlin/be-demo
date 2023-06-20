package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.common.CommonException;
import com.study.entity.UmsResource;
import com.study.mapper.UmsResourceMapper;
import com.study.service.UmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-14
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
    @Resource
    private UmsResourceMapper umsResourceMapper;

    @Override
    @CacheEvict(value = "ums", key = "'resources'")
    public boolean add(String name, Integer type, Integer level, String parentId, String frontUrl, String backUrl) {
        UmsResource umsResource = new UmsResource(
                name,
                type,
                level,
                parentId,
                frontUrl,
                backUrl
        );
        return this.save(umsResource);
    }

    @Override
    @CacheEvict(value = "ums", key = "'resources'")
    public boolean update(String id, String name, Integer type, String frontUrl, String backUrl) {
        UmsResource umsResource = new UmsResource(id, name, type, frontUrl, backUrl);
        return this.updateById(umsResource);
    }

    @Override
    public boolean check(String value, String id) {
        QueryWrapper<UmsResource> qw = new QueryWrapper<>();
        qw.eq("name", value);
        if (StringUtils.isNotBlank(id)) {
            qw.ne("id", id);
        }
        return this.count(qw) == 0;
    }

    private List<UmsResource> getByParentId(String parentId) {
        QueryWrapper<UmsResource> qw = new QueryWrapper<>();
        qw.eq("parent_id", parentId)
                .orderByDesc("sort");
        List<UmsResource> list = this.list(qw);
        for (UmsResource umsResource: list) {
            umsResource.setChildren(this.getByParentId(umsResource.getId()));
        }
        return list;
    }

    @Override
    @Cacheable(value = "ums", key = "'resources'")
    public List<UmsResource> getAll() {
        return this.getByParentId("");
    }

    @Override
    @CacheEvict(value = "ums", key = "'resources'")
    public boolean del(String id) {
        QueryWrapper<UmsResource> qw = new QueryWrapper<>();
        qw.eq("parent_id", id);
        if (this.count(qw) > 0) {
            throw new CommonException("存在未删除的下级数据，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    public List<UmsResource> getByUserId(String userId) {
        return this.umsResourceMapper.getByUserId(userId);
    }
}
