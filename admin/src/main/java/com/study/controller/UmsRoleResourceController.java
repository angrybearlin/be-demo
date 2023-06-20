package com.study.controller;


import com.study.common.ResultJson;
import com.study.service.UmsResourceService;
import com.study.service.UmsRoleResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色关联权限 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-16
 */
@RestController
@RequestMapping("/umsRoleResource")
public class UmsRoleResourceController {
    @Resource
    private UmsResourceService umsResourceService;
    @Resource
    private UmsRoleResourceService umsRoleResourceService;
    @GetMapping("/init")
    public ResultJson<Map> init(String roleId) {
        Map<String, List> map = new HashMap<>();
        map.put("resource", umsResourceService.getAll());
        map.put("checks", umsRoleResourceService.getResourceIdsByRoleId(roleId));
        return ResultJson.success(map);
    }

    @PostMapping("/save")
    public ResultJson<Boolean> save(String roleId, String[] menus, String[] btns) {
        return ResultJson.success(umsRoleResourceService.save(roleId, menus, btns), "保存角色权限关系成功");
    }
}
