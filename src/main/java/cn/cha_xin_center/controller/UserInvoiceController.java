package cn.cha_xin_center.controller;

import cn.cha_xin_center.common.R;
import cn.cha_xin_center.entity.UserInvoice;
import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.service.UserInvoiceService;
import cn.cha_xin_center.service.auth.UserAuth;
import cn.cha_xin_center.utils.IdCardUtil;
import cn.cha_xin_center.utils.TyshxydmVerificationUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author langci0913
 * @since 2024-06-07
 */
@RestController
@RequestMapping("/api/user")
public class UserInvoiceController {
    private static final Logger log = LoggerFactory.getLogger(UserInvoiceController.class);
    @Resource
    private UserInvoiceService userInvoiceService;

    @Resource
    private UserAuth userAuth;

    @PostMapping("/add/invoice")
    public R addInvoice(@RequestBody UserInvoice userInvoice) {
//        用户是否登录
        UserDto user = userAuth.getUser();
        if (user == null) {
            return R.error().message("请先登录后再试");
        }
//        登录的用户id
        Integer userId = user.getId();
//        传参是否设置默认发票
        boolean defaultInfo = userInvoice.isDefaultInfo();
        if (defaultInfo) {
            //        查询有没有设置默认的发票
            UserInvoice userInvoiceDb = userInvoiceService.findDefaultInvoiceByUserIdAndDefaultInfo(userId, 1);
            if (userInvoiceDb != null) {
                userInvoiceService.updateDefaultInfoById(userInvoiceDb.getId(),0);
            }
        }
//        判断参数是否为空
        String type = userInvoice.getType();
        if (type == null) {
            return R.error().message("发票类型不能为空");
        }
        String unitName = userInvoice.getUnitName();
        if (unitName == null) {
            return R.error().message("单位不能为空");
        }
        String idNum = userInvoice.getIdNum();
        if (idNum == null) {
            return R.error().message("纳税人识别号不能为空");
        }
        log.info("统一社会代码{}",TyshxydmVerificationUtil.isTyshxydm(idNum).equals("true"));
        log.info("身份证{}",IdCardUtil.isValidCard(idNum));
        if (TyshxydmVerificationUtil.isTyshxydm(idNum).equals("true") && IdCardUtil.isValidCard(idNum)){
            return R.error().message("身份证或统一社会代码不符合规范");
        }
        userInvoice.setUserId(userId);
        int i = userInvoiceService.addUserInvoiceByUid(userInvoice);
        if (i <= 0) {
            return R.error().message("新增发票失败");
        }

        return R.ok().message("新增发票成功");
    }

    @GetMapping("/find/invoice")
    public R findInvoice() {
        //        用户是否登录
        UserDto user = userAuth.getUser();
        System.out.println("user = " + user);
        if (user == null) {
            return R.error().message("请先登录后再试");
        }
//        登录的用户id
        Integer userId = user.getId();
        List<UserInvoice> userInvoice = userInvoiceService.findUserInvoiceByUid(userId);
        if (userInvoice == null) {
            return R.error().message("数据为空");
        }
        return R.ok().data("data", userInvoice);
    }

    @GetMapping("/del/invoice/{id}")
    public R delInvoice(@PathVariable String id) {
        if (id == null || StringUtils.isEmpty(id)) {
            return R.error().message("id不能为空");
        }
        UserInvoice userInvoiceById = userInvoiceService.findUserInvoiceById(Integer.valueOf(id));
        if (userInvoiceById == null) {
            return R.error().message("数据不存在");
        }
        UserDto user = userAuth.getUser();
        if (user == null) {
            return R.error().message("请先登录后再试");
        }
        Integer userId = user.getId();
        int i = userInvoiceService.deleteUserInvoiceById(userId,id);
        if (i<=0){
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @PostMapping("/update/invoice")
    public R updateInvoice(@RequestBody UserInvoice userInvoice){
//        判断用户是否登陆
        UserDto user = userAuth.getUser();
        System.out.println("user = " + user);
        if (user == null) {
            return R.error().message("请先登录后再试");
        }
//        获取传入发票id
        Integer id = userInvoice.getId();
        if (id.toString().isEmpty()) {
            return R.error().message("id不能为空");
        }
//        类型
        if (userInvoice.getType().isEmpty()) {
            return R.error().message("类型不能为空，请选择");
        }
//        身份证或统一社会代码
        String idNum = userInvoice.getIdNum();
        if (idNum == null) {
            return R.error().message("纳税人识别号不能为空");
        }
        if (TyshxydmVerificationUtil.isTyshxydm(idNum).equals("true") && IdCardUtil.isValidCard(idNum)){
            return R.error().message("身份证或统一社会代码不符合规范");
        }
//        是否默认发票
        boolean defaultInfo = userInvoice.isDefaultInfo();
        if (defaultInfo) {
            //        查询有没有设置默认的发票
            UserInvoice userInvoiceDb = userInvoiceService.findDefaultInvoiceByUserIdAndDefaultInfo(user.getId(), 1);
            if (userInvoiceDb != null) {
                userInvoiceService.updateDefaultInfoById(userInvoiceDb.getId(),0);
            }
        }
//        设置用户id
        userInvoice.setUserId(user.getId());
        int i = userInvoiceService.updateUserInvoice(userInvoice);
        if (i <= 0) {
            return R.error().message("修改失败");
        }
        return R.ok().message("修改成功");
    }

}
