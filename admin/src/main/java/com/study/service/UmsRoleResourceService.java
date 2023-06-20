package com.study.service;

import com.study.entity.UmsRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色关联权限 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-16
 */
public interface UmsRoleResourceService extends IService<UmsRoleResource> {
    boolean save(String roleId, String[] menus, String[] btns);
    List<String> getResourceIdsByRoleId(String roleId);
}
