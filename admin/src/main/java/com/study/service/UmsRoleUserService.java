package com.study.service;

import com.study.entity.UmsRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户和角色关联 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
public interface UmsRoleUserService extends IService<UmsRoleUser> {
    List<String> getUserIdsByRoleId(String roleId);
    boolean save(String roleId, String[] values);
}
