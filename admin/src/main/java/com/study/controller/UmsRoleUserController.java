package com.study.controller;


import com.study.common.ResultJson;
import com.study.service.UmsRoleUserService;
import com.study.service.UmsUserService;
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
 * 用户和角色关联 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/umsRoleUser")
public class UmsRoleUserController {
    @Resource
    private UmsUserService umsUserService;
    @Resource
    private UmsRoleUserService umsRoleUserService;
    @GetMapping("/init")
    public ResultJson<Map> init(String roleId) {
        Map<String, List> map = new HashMap<>();
        map.put("users", umsUserService.getActive());
        map.put("values", umsRoleUserService.getUserIdsByRoleId(roleId));
        return ResultJson.success(map);
    }

    @PostMapping("/save")
    public ResultJson<Boolean> save(String roleId, String[] values) {
        return ResultJson.success(umsRoleUserService.save(roleId, values), "关联用户成功");
    }
}
