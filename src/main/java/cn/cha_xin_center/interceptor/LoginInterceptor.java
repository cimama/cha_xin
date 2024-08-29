package cn.cha_xin_center.interceptor;

import cn.cha_xin_center.service.auth.UserAuth;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserAuth userAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("拦截路径==========>{},是否放行==========>{}", request.getRequestURI(),userAuth.isLogin());
        return userAuth.isLogin();
    }


}
