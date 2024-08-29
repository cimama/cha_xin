package cn.cha_xin_center.mapper;

import cn.cha_xin_center.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author langci0913
 * @since 2024-06-07
 */
@Mapper
public interface UserAddressMapper {
    int addUserAddressByUid(UserAddress userAddress);
    UserAddress findDefaultAddressByUserIdAndDefaultInfo(@Param("userId") Integer userId, @Param("defaultInfo") int defaultInfo);
    void updateDefaultInfoById(@Param("id") Integer id, @Param("defaultInfo") int defaultInfo);
    List<UserAddress> findUserAddressByUid(Integer userId);
    int deleteUserAddressById(@Param("userId") Integer userId, @Param("id") String id);
    UserAddress findUserAddressById(Integer id);
    int updateUserAddress(UserAddress userAddress);
}
