package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.UmsRole;
import com.study.mapper.UmsRoleMapper;
import com.study.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {
    @Override
    public List<UmsRole> list(String value) {
        QueryWrapper<UmsRole> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(value)) {
            qw.like("name", value);
        }
        qw.orderByDesc("sort");
        return this.list(qw);
    }

    @Override
    public boolean add(String name) {
        UmsRole umsRole = new UmsRole(name);
        return this.save(umsRole);
    }

    @Override
    public boolean update(String id, String name) {
        UmsRole umsRole = new UmsRole(id, name);
        return this.updateById(umsRole);
    }

    @Override
    public boolean del(String id, Boolean active) {
        return this.updateById(new UmsRole(id, active));
    }

    @Override
    public boolean check(String field, String value) {
        QueryWrapper<UmsRole> qw = new QueryWrapper<>();
        qw.eq(field, value);
        return this.count(qw) == 0;
    }
}
