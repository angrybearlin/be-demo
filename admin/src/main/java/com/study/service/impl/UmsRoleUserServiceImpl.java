package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.entity.UmsRoleUser;
import com.study.mapper.UmsRoleUserMapper;
import com.study.service.UmsRoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户和角色关联 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@Service
public class UmsRoleUserServiceImpl extends ServiceImpl<UmsRoleUserMapper, UmsRoleUser> implements UmsRoleUserService {
    @Override
    public List<String> getUserIdsByRoleId(String roleId) {
        QueryWrapper<UmsRoleUser> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        List<UmsRoleUser> list = this.list(qw);
        List<String> userIds = new ArrayList<>();
        list.forEach(entry -> {
            userIds.add(entry.getUserId());
        });
        return userIds;
    }

    @Override
    @Transactional
    public boolean save(String roleId, String[] values) {
        // 先把角色下关联的所有数据清空
        this.removeByRoleId(roleId);
        // 再添加
        List<UmsRoleUser> list = new ArrayList<>();
        if (null != values) {
            for (String userId: values) {
                list.add(new UmsRoleUser(roleId, userId));
            }
        }
        return this.saveBatch(list);
    }

    private void removeByRoleId(String roleId) {
        UpdateWrapper<UmsRoleUser> uw = new UpdateWrapper<>();
        uw.eq("role_id", roleId);
        this.remove(uw);
    }
}
