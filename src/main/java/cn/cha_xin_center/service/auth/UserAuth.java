package cn.cha_xin_center.service.auth;

import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.service.UsersService;
import cn.cha_xin_center.utils.CacheService;
import cn.cha_xin_center.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserAuth {
    private static final Logger log = LoggerFactory.getLogger(UserAuth.class);
    @Resource
    private HttpServletRequest request;

    @Resource
    private CacheService cacheService;

    @Resource
    private UsersService usersService;

    public boolean isLogin() {
        String token = request.getHeader("token");
        log.info("获取到的token=========={}",token);
        if (token == null || token.isEmpty()) {
            return false;
        }
        if (!JwtUtils.checkToken(token)) {
            return false;
        }
        String phone = JwtUtils.getTokenInfo(token).getClaim("phone").asString();
        log.info("获取到的phone=========={}",phone);

        if (phone == null || phone.isEmpty()) {
            return false;
        }
        String redisToken = cacheService.get("LOGIN_STATUS_" + phone);
        if (redisToken == null) {
            return false;
        }
        redisToken = redisToken.replaceAll("\"", "");
        log.info("获取到的redisToken=========={}",redisToken);
        log.info("比对==========>{}",redisToken.equals(token));
        return redisToken.equals(token);
    }
    //    根据token获取用户信息
    public UserDto getUser() {
        if (!isLogin()) {
            return null;
        }
        String token = request.getHeader("token");
//        得到手机号
        String phone = JwtUtils.getTokenInfo(token).getClaim("phone").asString();
        return usersService.findUserByPhone(phone);
    }
}