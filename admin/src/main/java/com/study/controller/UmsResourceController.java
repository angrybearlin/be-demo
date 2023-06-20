package com.study.controller;


import com.study.common.ResultJson;
import com.study.entity.UmsResource;
import com.study.service.UmsResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/umsResource")
public class UmsResourceController {
    @Resource
    private UmsResourceService umsResourceService;
    @GetMapping("/list")
    public ResultJson<List> list() {
        return ResultJson.success(umsResourceService.getAll());
    }
    @PostMapping("/add")
    public ResultJson<Boolean> add(String name, Integer type, Integer level, String parentId, String frontUrl, String backUrl) {
        return ResultJson.success(umsResourceService.add(name, type, level, parentId, frontUrl, backUrl), "添加资源成功");
    }
    @PostMapping("/update")
    public ResultJson<Boolean> update(String id, String name, Integer type, String frontUrl, String backUrl) {
        return ResultJson.success(umsResourceService.update(id, name, type, frontUrl, backUrl), "修改资源成功");
    }

    @GetMapping("/check")
    public ResultJson<Boolean> check(String value, String id) {
        return ResultJson.success(umsResourceService.check(value, id));
    }

    @GetMapping("/getById")
    public ResultJson<UmsResource> getById(String id) {
        return ResultJson.success(umsResourceService.getById(id));
    }

    @PostMapping("/del")
    public ResultJson<Boolean> del(String id) {
        return ResultJson.success(umsResourceService.del(id), "删除资源成功");
    }
}
