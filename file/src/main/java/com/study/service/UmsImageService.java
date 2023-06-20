package com.study.service;

import com.study.entity.UmsImage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author linkexuan
 * @since 2023-03-10
 */
public interface UmsImageService extends IService<UmsImage> {
    UmsImage get(String md5, Long size, String contentType);

    String upload(MultipartFile file, String bucket) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
