package cn.cha_xin_center.entity;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author langci0913
 * @since 2024-06-07
 */
@Data
public class UserInvoice {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 发票类型
     */
    private String type;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 纳税人识别号
     */
    private String idNum;

    /**
     * 是否默认
     */
    private boolean defaultInfo;

    /**
     * 用户id
     */
    private Integer userId;
}
