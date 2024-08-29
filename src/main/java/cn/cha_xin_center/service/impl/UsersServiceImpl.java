package cn.cha_xin_center.service.impl;

import cn.cha_xin_center.entity.Users;
import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.mapper.UsersMapper;
import cn.cha_xin_center.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author langci0913
 * @since 2024-06-06
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UsersMapper usersMapper;


    @Override
    public int addUser(Users user) {
        return usersMapper.addUser(user);
    }

    @Override
    public UserDto findUserByPhone(String phone) {
        return usersMapper.findUserByPhone(phone);
    }

}
