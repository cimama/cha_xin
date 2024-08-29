package cn.cha_xin_center.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {


//    验证文件大小
    public static boolean checkFileSize(MultipartFile file, long size){
        long fileSize = file.getSize();
        return fileSize <= size * 1024 * 1024;
    }

}
