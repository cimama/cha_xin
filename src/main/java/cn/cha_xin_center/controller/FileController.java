package cn.cha_xin_center.controller;

import cn.cha_xin_center.common.R;
import cn.cha_xin_center.config.MinioConfig;
import cn.cha_xin_center.utils.MinioUtil;
import io.minio.messages.Bucket;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {
 
    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private MinioConfig prop;
 
    @ApiOperation(value = "查看存储bucket是否存在")
    @GetMapping("/bucketExists")
    public R bucketExists(@RequestParam("bucketName") String bucketName) {
        return R.ok().data("bucketName",minioUtil.bucketExists(bucketName));
    }
 
    @ApiOperation(value = "创建存储bucket")
    @GetMapping("/makeBucket")
    public R makeBucket(String bucketName) {
        return R.ok().data("bucketName",minioUtil.makeBucket(bucketName));
    }
 
    @ApiOperation(value = "删除存储bucket")
    @GetMapping("/removeBucket")
    public R removeBucket(String bucketName) {
        return R.ok().data("bucketName",minioUtil.removeBucket(bucketName));
    }
 
    @ApiOperation(value = "获取全部bucket")
    @GetMapping("/getAllBuckets")
    public R getAllBuckets() {
        List<Bucket> allBuckets = minioUtil.getAllBuckets();
        return R.ok().data("allBuckets",allBuckets);
    }
 
    @ApiOperation(value = "文件上传返回url")
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return R.ok().data("url",(prop.getEndpoint() + "/" + prop.getBucketName() +
            "/" + objectName));
        }
        return R.error();
    }
 
    @ApiOperation(value = "图片/视频预览")
    @GetMapping("/preview")
    public R preview(@RequestParam("fileName") String fileName) {
        return R.ok().data("filleName",minioUtil.preview(fileName));
    }
 
    @ApiOperation(value = "文件下载")
    @GetMapping("/download")
    public R download(@RequestParam("fileName") String fileName, HttpServletResponse res)
    {
        minioUtil.download(fileName,res);
        return R.ok();
    }
 
    @ApiOperation(value = "删除文件", notes = "根据url地址删除文件")
    @PostMapping("/delete")
    public R remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + 
        prop.getBucketName().length()+1);
        minioUtil.remove(objName);
        return R.ok().data("objName",objName);
    }
 
}