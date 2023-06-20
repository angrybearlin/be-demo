package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.entity.PmsBrand;
import com.study.feignApi.UmsFileService;
import com.study.mapper.PmsBrandMapper;
import com.study.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-12
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {
    @Resource
    private UmsFileService umsFileService;
    @Override
    public IPage<PmsBrand> list(Integer pageNo, Integer pageSize, String value) {
        QueryWrapper<PmsBrand> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(value)) {
            qw.like("name", value);
        }
        return this.page(new Page<>(pageNo, pageSize), qw);
    }

    @Override
    public boolean add(String name, String firstLetter, MultipartFile file, String description) {
        PmsBrand pmsBrand = new PmsBrand(
                name,
                firstLetter,
                umsFileService.upload(file),
                description
        );
        return this.save(pmsBrand);
    }

    @Override
    public boolean update(String id, String name, String firstLetter, MultipartFile file, String description) {
        PmsBrand pmsBrand = new PmsBrand(name, firstLetter, description);
        pmsBrand.setId(id);
        if (null != file) {
            pmsBrand.setLogo(umsFileService.upload(file));
        }
        return this.updateById(pmsBrand);
    }

    @Override
    public boolean del(String id, Boolean active) {
        return this.updateById(new PmsBrand(id, active));
    }

    @Override
    public boolean check(String field, String name, String id) {
        QueryWrapper<PmsBrand> qw = new QueryWrapper<>();
        qw.eq(field, name);
        if (StringUtils.isNotBlank(id)) {
            qw.ne("id", id);
        }
        return this.count(qw) == 0;
    }
}
