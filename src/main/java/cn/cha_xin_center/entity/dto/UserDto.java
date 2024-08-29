package cn.cha_xin_center.entity.dto;

import lombok.Data;

@Data
public class UserDto {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;
}
