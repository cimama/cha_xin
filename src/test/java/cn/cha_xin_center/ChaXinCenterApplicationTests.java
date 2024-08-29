package cn.cha_xin_center;

import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.service.UsersService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChaXinCenterApplicationTests {
    @Resource
    private UsersService usersService;

    @Test
    void contextLoads() {
        UserDto userByPhone = usersService.findUserByPhone("13791453657");
        System.out.println("userByPhone = " + userByPhone);
    }

}
