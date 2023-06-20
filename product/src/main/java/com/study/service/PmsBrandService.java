package com.study.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.entity.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-12
 */
public interface PmsBrandService extends IService<PmsBrand> {
    IPage<PmsBrand> list(Integer pageNo, Integer pageSize, String value);
    boolean add(String name, String firstLetter, MultipartFile file, String description);
    boolean update(String id, String name, String firstLetter, MultipartFile file, String description);
    boolean del(String id, Boolean active);
    boolean check(String field, String name, String id);
}
