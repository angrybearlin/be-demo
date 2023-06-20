package com.study.service;

import com.study.entity.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
public interface UmsRoleService extends IService<UmsRole> {
    List<UmsRole> list(String value);
    boolean add(String name);
    boolean update(String id, String name);
    boolean del(String id, Boolean active);
    boolean check(String field, String value);
}
