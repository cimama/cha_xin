package cn.cha_xin_center.service.impl;

import cn.cha_xin_center.entity.UserInvoice;
import cn.cha_xin_center.mapper.UserInvoiceMapper;
import cn.cha_xin_center.service.UserInvoiceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author langci0913
 * @since 2024-06-07
 */
@Service
public class UserInvoiceServiceImpl implements UserInvoiceService {

    @Resource
    private UserInvoiceMapper userInvoiceMapper;

    @Override
    public int addUserInvoiceByUid(UserInvoice userInvoice) {
        return userInvoiceMapper.addUserInvoiceByUid(userInvoice);
    }

    @Override
    public UserInvoice findDefaultInvoiceByUserIdAndDefaultInfo(Integer userId, int defaultInfo) {
        return userInvoiceMapper.findDefaultInvoiceByUserIdAndDefaultInfo(userId, defaultInfo);
    }

    @Override
    public void updateDefaultInfoById(Integer id, int defaultInfo) {
        userInvoiceMapper.updateDefaultInfoById(id, defaultInfo);
    }

    @Override
    public List<UserInvoice> findUserInvoiceByUid(Integer userId) {
        return userInvoiceMapper.findUserInvoiceByUid(userId);
    }

    @Override
    public int deleteUserInvoiceById(Integer userId, String id) {
        return userInvoiceMapper.deleteUserInvoiceById(userId, id);
    }

    @Override
    public UserInvoice findUserInvoiceById(Integer id) {
        return userInvoiceMapper.findUserInvoiceById(id);
    }

    @Override
    public int updateUserInvoice(UserInvoice userInvoice) {
        return userInvoiceMapper.updateUserInvoice(userInvoice);
    }

}
