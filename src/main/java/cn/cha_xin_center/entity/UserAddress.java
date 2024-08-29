package cn.cha_xin_center.entity;

import lombok.Data;

@Data
public class UserAddress {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 是否默认
     */
    private boolean defaultInfo;
}
