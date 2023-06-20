package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.common.CommonException;
import com.study.common.JwtUtil;
import com.study.entity.UmsResource;
import com.study.entity.UmsUser;
import com.study.feignApi.UmsFileService;
import com.study.mapper.UmsUserMapper;
import com.study.service.UmsResourceService;
import com.study.service.UmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-02
 */
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {
    @Resource
    private UmsFileService umsFileService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UmsResourceService umsResourceService;
    @Resource(name = "lkxRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public IPage<UmsUser> list(Integer pageNo, Integer pageSize, String value) {
        QueryWrapper<UmsUser> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(value)) {
            qw.like("name", value);
        }
        qw.orderByDesc("sort");
        return this.page(new Page<>(pageNo, pageSize), qw);
    }

    @Override
    public boolean add(String name, String phone, String email, Integer gender, MultipartFile file) {
        UmsUser umsUser = new UmsUser(name, phone, email, gender, passwordEncoder.encode(phone));
        umsUser.setIcon(umsFileService.upload(file));
        return this.save(umsUser);
    }

    @Override
    public boolean check(String field, String value, String id) {
        QueryWrapper<UmsUser> qw = new QueryWrapper<>();
        qw.eq(field, value);
        if (StringUtils.isNotBlank(id)) {
            qw.ne("id", id);
        }
        return this.count(qw) == 0;
    }

    @Override
    public boolean del(String id, Boolean active) {
        return this.updateById(new UmsUser(id, active));
    }

    @Override
    public boolean update(String id, String name, String phone, String email, Integer gender, MultipartFile file) {
        UmsUser umsUser = new UmsUser(id, name, phone, email, gender);
        if (null != file) {
            umsUser.setIcon(umsFileService.upload(file));
        }
        return this.updateById(umsUser);
    }

    @Override
    public List<UmsUser> getActive() {
        QueryWrapper<UmsUser> qw = new QueryWrapper<>();
        qw.eq("active", true);
        return this.list(qw);
    }

    @Override
    public UmsUser getByUserName(String username, String password) {
        QueryWrapper<UmsUser> qw = new QueryWrapper<>();
        qw.eq("phone", username)
                .or().eq("email", username);
        UmsUser user = this.getOne(qw);
        if (null == user || !passwordEncoder.matches(password, user.getPassword())) {
            throw new CommonException("用户名或密码错误");
        }
        if (!user.getActive()) {
            throw new CommonException("该用户已经禁用");
        }
        return user;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        // 获取token
        UmsUser user = this.getByUserName(username, password);
        map.put("userId", user.getId());
        map.put("token", JwtUtil.create(user.getId()));
        // 获取所有权限
        List<UmsResource> resource = umsResourceService.getByUserId(user.getId());
        // 拆分权限
        Map<String, Collection> split = this.split(resource);
        map.put("menu", split.get("menu"));
        // 把后端权限保存到Redis中
        String key = "ums::login::" + user.getId();
        redisTemplate.opsForValue().set(key, split.get("btn"), 30, TimeUnit.MINUTES);
        return map;
    }

    /**
     * 分离前后端权限
     * @return
     */
    public Map<String, Collection> split(List<UmsResource> source) {
        Map<String, Collection> map = new HashMap<>();

        List<UmsResource> menu = new ArrayList<>();
        Set<String> backUrls = new HashSet<>();
        for (UmsResource umsResource: source) {
            if (umsResource.getLevel().intValue() == 1) {
                menu.add(umsResource);
                continue;
            }
            if (umsResource.getType().intValue() == 1) {
                for (UmsResource parent: source) {
                    if (umsResource.getParentId().equals(parent.getId())) {
                        parent.getChildren().add(umsResource);
                        break;
                    }
                }
            } else {
                backUrls.add(umsResource.getBackUrl());
            }
        }
        map.put("menu", menu);
        map.put("btn", backUrls);
        return map;
    }
}
