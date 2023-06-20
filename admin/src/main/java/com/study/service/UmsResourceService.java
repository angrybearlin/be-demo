package com.study.service;

import com.study.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-14
 */
public interface UmsResourceService extends IService<UmsResource> {
    List<UmsResource> getAll();
    boolean add(String name, Integer type, Integer level, String parentId, String frontUrl, String backUrl);
    boolean update(String id, String name, Integer type, String frontUrl, String backUrl);
    boolean check(String value, String id);
    boolean del(String id);
    List<UmsResource> getByUserId(String userId);
}
