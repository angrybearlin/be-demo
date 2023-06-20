package com.study.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.entity.UmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-02
 */
public interface UmsUserService extends IService<UmsUser> {
    IPage<UmsUser> list(Integer pageNo, Integer pageSize, String value);

    boolean add(String name, String phone, String email, Integer gender, MultipartFile file);

    boolean check(String field, String value, String id);

    boolean del(String id, Boolean active);

    boolean update(String id, String name, String phone, String email, Integer gender, MultipartFile file);

    List<UmsUser> getActive();
    UmsUser getByUserName(String username, String password);
    Map<String, Object> login(String username, String password);
}
