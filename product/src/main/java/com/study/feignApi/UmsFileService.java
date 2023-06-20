package com.study.feignApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("file")
public interface UmsFileService {
    @PostMapping(value = "/file/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(MultipartFile file);
}
