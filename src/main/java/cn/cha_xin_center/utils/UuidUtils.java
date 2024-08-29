package cn.cha_xin_center.utils;
import java.util.UUID;
/**
 * 产生uuid随机字符串工具类。
 */
public class UuidUtils {
    private UuidUtils(){}
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
