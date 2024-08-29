package cn.cha_xin_center.utils;

import java.util.Random;

/**
 * 随机验证码工具类
 */
public class VerificationCodeUtil {
    private static final String CHARACTERS = "0123456789";
    private static final Random random = new Random();

    public static String generateVerificationCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

}
