package cn.cha_xin_center.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file.upload")
@Component
@Data
public class FileBean {

    private String dir;

    private String unAllow;
}
