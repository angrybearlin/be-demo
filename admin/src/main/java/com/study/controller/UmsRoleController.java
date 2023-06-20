package com.study.controller;


import com.study.common.ResultJson;
import com.study.entity.UmsRole;
import com.study.service.UmsRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/umsRole")
public class UmsRoleController {

    @Resource
    private UmsRoleService umsRoleService;
    @GetMapping("/list")
    public ResultJson<List> list(String name) {
        return ResultJson.success(umsRoleService.list(name));
    }

    @PostMapping("/add")
    public ResultJson<Boolean> add(String name) {
        return ResultJson.success(umsRoleService.add(name), "添加角色成功");
    }

    @PostMapping("/update")
    public ResultJson<Boolean> update(String id, String name) {
        return ResultJson.success(umsRoleService.update(id, name), "修改角色成功");
    }

    @GetMapping("/check")
    public ResultJson<Boolean> check(String field, String value) {
        return ResultJson.success(umsRoleService.check(field, value));
    }

    @PostMapping("/del")
    public ResultJson<Boolean> del(String id, Boolean active) {
        return ResultJson.success(umsRoleService.del(id, active), active ? "启用数据成功" : "禁用数据成功");
    }

    @GetMapping("/getById")
    public ResultJson<UmsRole> getById(String id) {
        return ResultJson.success(umsRoleService.getById(id));
    }
}
