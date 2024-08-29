package cn.cha_xin_center.config;

import cn.cha_xin_center.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 配置拦截器
     *
     * @param registry 相当于拦截器的注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，并指定拦截路径
        registry.addInterceptor(loginInterceptor())
                // 拦截所有请求
                .addPathPatterns("/**")
                // 不拦截登录和注册请求
                .excludePathPatterns("/user/auth/**", "/static/**", "/indexes/**","/file/**");
    }
}