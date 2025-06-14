package com.example.controller;


import com.example.pojo.Result;
import com.example.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

       /**
     * 处理文件上传请求，将接收到的文件保存到指定目录并返回访问地址。
     *
     * @param file 上传的MultipartFile对象，包含客户端提交的文件数据
     * @return Result<String> 包含文件访问地址的成功响应对象
     * @throws IOException 当文件存储过程中发生I/O异常时抛出
     */
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 将上传的文件转存到指定物理路径（需确保目录存在且有写入权限）
//        file.transferTo(new File("D:\\Juzel\\Desktop\\fileTest\\" + filename));
       String url = AliOssUtil.uploadFile(filename, file.getInputStream());

        return Result.success(url);
    }

}
