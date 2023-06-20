package com.study.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.common.ResultJson;
import com.study.entity.UmsUser;
import com.study.service.UmsUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-02
 */
@RestController
@RequestMapping("/umsUser")
public class UmsUserController {
    @Resource
    private UmsUserService umsUserService;
    @RequestMapping("/list")
    public ResultJson<IPage> list(Integer pageNo, Integer pageSize, String value) {
        return ResultJson.success(umsUserService.list(pageNo, pageSize, value));
    }

    @PostMapping("/add")
    public ResultJson<Boolean> add(String name, String phone, String email, Integer gender, MultipartFile file) {
        return ResultJson.success(umsUserService.add(name, phone, email, gender, file), "添加用户成功");
    }

    @GetMapping("/check")
    public ResultJson<Boolean> check(String field, String value, String id) {
        return ResultJson.success(umsUserService.check(field, value, id));
    }

    @PostMapping("/del")
    public ResultJson<Boolean> del(String id, Boolean active) {
        return ResultJson.success(umsUserService.del(id, active), active ? "启用数据成功" : "禁用数据成功");
    }

    @GetMapping("/getById")
    public ResultJson<UmsUser> getById(String id) {
        return ResultJson.success(umsUserService.getById(id));
    }

    @PostMapping("/update")
    public ResultJson<Boolean> update(String id, String name, String phone, String email, Integer gender, MultipartFile file) {
        return ResultJson.success(umsUserService.update(id, name, phone, email, gender, file), "修改用户成功");
    }
}
