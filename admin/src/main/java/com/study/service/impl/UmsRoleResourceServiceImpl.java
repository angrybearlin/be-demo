package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.entity.UmsRoleResource;
import com.study.mapper.UmsRoleResourceMapper;
import com.study.service.UmsRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色关联权限 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-16
 */
@Service
public class UmsRoleResourceServiceImpl extends ServiceImpl<UmsRoleResourceMapper, UmsRoleResource> implements UmsRoleResourceService {
    private void removeByRoleId(String roleId) {
        UpdateWrapper<UmsRoleResource> uw =new UpdateWrapper<>();
        uw.eq("role_id", roleId);
        this.remove(uw);
    }
    @Override
    @Transactional
    public boolean save(String roleId, String[] menus, String[] btns) {
        this.removeByRoleId(roleId);
        List<UmsRoleResource> list = new ArrayList<>();
        if (null != menus) {
            for (String resourceId: menus) {
                list.add(new UmsRoleResource(roleId, resourceId, 1));
            }
        }
        if (null != btns) {
            for (String resourceId: btns) {
                list.add(new UmsRoleResource(roleId, resourceId, 0));
            }
        }
        return this.saveBatch(list);
    }

    @Override
    public List<String> getResourceIdsByRoleId(String roleId) {
        List<String> resourceIds = new ArrayList<>();
        QueryWrapper<UmsRoleResource> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId)
                .eq("resource_type", 0);
        List<UmsRoleResource> list = this.list(qw);
        list.forEach(entry -> {
            resourceIds.add(entry.getResourceId());
        });
        return resourceIds;
    }
}
