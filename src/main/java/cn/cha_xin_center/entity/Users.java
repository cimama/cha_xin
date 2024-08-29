package cn.cha_xin_center.entity;

import lombok.Data;


/**
 * <p>
 * 用户表
 * </p>
 * @author langci0913
 * @since 2024-06-06
 */
@Data
public class Users  {


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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
