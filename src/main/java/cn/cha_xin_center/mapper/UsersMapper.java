package cn.cha_xin_center.mapper;

import cn.cha_xin_center.entity.Users;
import cn.cha_xin_center.entity.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author langci0913
 * @since 2024-06-06
 */
@Mapper
public interface UsersMapper {
    int addUser(Users user);
    UserDto findUserByPhone(String phone);
}
