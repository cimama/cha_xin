package cn.cha_xin_center.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcCorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 匹配所有的路径
                .allowCredentials(true) // 设置允许凭证
                .allowedHeaders("*")   // 设置请求头
                .allowedMethods("GET","POST","PUT","DELETE") // 设置允许的方式
                .allowedOriginPatterns("*");
    }
}