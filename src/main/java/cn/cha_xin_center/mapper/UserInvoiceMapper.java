package cn.cha_xin_center.mapper;

import cn.cha_xin_center.entity.UserInvoice;
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
public interface UserInvoiceMapper {
    int addUserInvoiceByUid(UserInvoice userInvoice);
    UserInvoice findDefaultInvoiceByUserIdAndDefaultInfo(@Param("userId") Integer userId, @Param("defaultInfo") int defaultInfo);
    void updateDefaultInfoById(@Param("id") Integer id, @Param("defaultInfo") int defaultInfo);
    List<UserInvoice> findUserInvoiceByUid(Integer userId);
    int deleteUserInvoiceById(@Param("userId") Integer userId, @Param("id") String id);
    UserInvoice findUserInvoiceById(Integer id);
    int updateUserInvoice(UserInvoice userInvoice);

}
