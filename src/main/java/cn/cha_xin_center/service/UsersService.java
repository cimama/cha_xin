package cn.cha_xin_center.service;

import cn.cha_xin_center.entity.Users;
import cn.cha_xin_center.entity.dto.UserDto;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author langci0913
 * @since 2024-06-06
 */
public interface UsersService  {
    int addUser(Users user);
    UserDto findUserByPhone(String phone);
}
