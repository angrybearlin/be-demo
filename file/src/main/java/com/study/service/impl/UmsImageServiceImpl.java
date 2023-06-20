package com.study.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.UmsImage;
import com.study.mapper.UmsImageMapper;
import com.study.service.UmsImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-10
 */
@Service
public class UmsImageServiceImpl extends ServiceImpl<UmsImageMapper, UmsImage> implements UmsImageService {
    @Value("${minio.config.endpoint}")
    private String endpoint;
    @Value("${minio.config.username}")
    private String username;
    @Value("${minio.config.password}")
    private String password;

    @Override
    public UmsImage get(String md5, Long size, String contentType) {
        QueryWrapper<UmsImage> qw = new QueryWrapper<>();
        qw.eq("md5", md5)
                .eq("size", size)
                .eq("content_type", contentType);
        return this.getOne(qw);
    }

    @Override
    @Transactional
    public String upload(MultipartFile file, String bucket) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 获取文件的md5大小和类型
        String md5 = DigestUtils.md5Hex(file.getInputStream());
        long size = file.getSize();
        String contentType = file.getContentType();
        // 查询是否上传过
        UmsImage umsImage = this.get(md5, size, contentType);
        // 如果上传过，直接返回曾经上传的路径
        if (null != umsImage) {
            return umsImage.getPath();
        }
        // 如果没上传过，创建minio客户端
        MinioClient client = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
        StringBuilder builder = new StringBuilder();
        // 文件重新命名
        builder.append(NanoIdUtils.randomNanoId())
                .append(".")
                .append(FilenameUtils.getExtension(file.getOriginalFilename()));
        // 定义上传参数
        PutObjectArgs args = PutObjectArgs.builder()
                .object(builder.toString())
                .bucket(bucket)
                .contentType(contentType)
                .stream(file.getInputStream(), size, 0)
                .build();
        // 定义path
        String path = bucket + "/" + builder.toString();
        // 保存到数据库
        umsImage = new UmsImage(md5, size, contentType, path);
        this.save(umsImage);
        // 上传文件
        client.putObject(args);
        // 返回路径
        return path;
    }
}
