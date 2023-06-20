package com.study.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.common.ResultJson;
import com.study.entity.PmsBrand;
import com.study.service.PmsBrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-12
 */
@RestController
@RequestMapping("/pmsBrand")
public class PmsBrandController {
    @Resource
    private PmsBrandService pmsBrandService;
    @GetMapping("/list")
    ResultJson<IPage> list(Integer pageNo, Integer pageSize, String value) {
        return ResultJson.success(pmsBrandService.list(pageNo, pageSize, value));
    }
    @PostMapping("/add")
    ResultJson<Boolean> add(String name, String firstLetter, MultipartFile file, String description) {
        return ResultJson.success(pmsBrandService.add(name, firstLetter, file, description), "添加品牌成功");
    }

    @PostMapping("/update")
    ResultJson<Boolean> update(String id, String name, String firstLetter, MultipartFile file, String description) {
        return ResultJson.success(pmsBrandService.update(id, name, firstLetter, file, description), "修改品牌成功");
    }

    @PostMapping("/del")
    ResultJson<Boolean> del(String id, Boolean active) {
        return ResultJson.success(pmsBrandService.del(id, active), active ? "启用品牌成功" : "禁用品牌成功");
    }

    @GetMapping("/getById")
    ResultJson<PmsBrand> getById(String id) {
        return ResultJson.success(pmsBrandService.getById(id));
    }

    @GetMapping("/check")
    ResultJson<Boolean> check(String field, String value, String id) {
        return ResultJson.success(pmsBrandService.check(field, value, id));
    }
}
