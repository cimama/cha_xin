package cn.cha_xin_center.controller;

import cn.cha_xin_center.common.R;
import cn.cha_xin_center.entity.Users;
import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.service.UsersService;
import cn.cha_xin_center.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author langci0913
 * @since 2024-06-06
 */
@RestController
@RequestMapping("/user/auth")
public class UsersController {

    @Resource
    private UsersService usersService;

    @Resource
    private CacheService cacheService;

    private static final ObjectMapper mapper = new ObjectMapper();
    //    过期时间 5分钟
    private static final long EXPIRATION = 5;

    /**
     * 注册发送验证码
     *
     * @param phone 手机号
     * @return 返回json
     * @throws Exception 异常
     */
    @PostMapping("/register/send/sms")
    public R registerSendSms(String phone) throws Exception {
        if (phone == null) {
            return R.error().message("手机号不能为空");
        }
        //        检查手机号是否正确
        boolean checkPhone = CheckPhoneHelper.validator(phone);
        if (!checkPhone) {
            return R.error().message("手机号格式不正确");
        }
//        判断redis是否存在验证码，如果存在避免5分钟内重复发送
        if (Boolean.TRUE.equals(cacheService.hasKey("CREATE_SMS_" + phone))) {
            return R.ok().message("验证码已下发至手机，请勿重复发送");
        }
//        随机验证码
        String code = VerificationCodeUtil.generateVerificationCode(6);
//        发送验证码到手机号
        SendSmsUtil.sendSms(code, phone);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
//        存储验证码手机号到redis
        cacheService.add("CREATE_SMS_" + phone,map,EXPIRATION,TimeUnit.MINUTES);
        return R.ok().message("验证码发送成功");
    }

    //    注册
    @PostMapping("/register/by/sms")
    public R register(Users user, String code) throws JsonProcessingException {
//        检查手机号是否正确
        boolean checkPhone = CheckPhoneHelper.validator(user.getPhone());
        if (!checkPhone) {
            return R.error().message("手机号错误");
        }
//        检查数据库是否存在
        UserDto userByPhone = usersService.findUserByPhone(user.getPhone());
//        如果手机号存在
        if (userByPhone != null) {
            return R.error().message("已有账号请登录");
        }
//        检查验证码
        String redisSms = cacheService.get("CREATE_SMS_" + user.getPhone());
        if (redisSms == null) {
            return R.error().message("请重新发送验证码后再试");
        }
        Map<String, String> map = mapper.readValue(redisSms, Map.class);
        String storedCode = map.get("code");
        if (!code.equals(storedCode)) {
            return R.error().message("验证码错误");
        }
//        密码校验
        String password = user.getPassword();
        if (password == null) {
            return R.error().message("密码不能为空");
        }
        if (password.length() < 6) {
            return R.error().message("密码不能小于6位字符");
        }
        String encryptPassword = KaisaUtil.encryptKaisa(password, 13);
//        设置加密密码
        user.setPassword(encryptPassword);
//        设置用户名
        String username = maskPhoneNumber(user.getPhone());
        user.setUsername(username);
//        设置创建时间和更新时间
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setCreateTime(time);
        user.setUpdateTime(time);
//        如果添加成功
        int i = usersService.addUser(user);
//        移除验证码
        removeRedisSms(user.getPhone());
        if (i > 0) {
            return R.ok().message("注册成功");
        } else {
            return R.error().message("注册失败");
        }
    }

    //    用户名规则
    public static String maskPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
    }

    //    redis移除注册验证码
    public void removeRedisSms(String phone) {
        cacheService.delete("CREATE_SMS_" + phone);
    }

    /**
     * 手机号密码登录
     * @param user 用户
     * @return json
     */
    @PostMapping("/login/common")
    public R login(UserDto user) {
        String phone = user.getPhone();
        if (phone == null) {
            return R.error().message("手机号不能为空");
        }
        if (!CheckPhoneHelper.validator(phone)) {
            return R.error().message("手机号格式不正确");
        }
        String password = user.getPassword();
        if (password == null) {
            return R.error().message("密码不能为空");
        }
        UserDto userByPhone = usersService.findUserByPhone(phone);
//        如果不存在报错
        if (userByPhone == null) {
            return R.error().message("手机号或密码错误");
        }
//        对比密码
        if (!KaisaUtil.decryptKaiser(userByPhone.getPassword(),13).equals(password)) {
            return R.error().message("密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("id", userByPhone.getId().toString());
        map.put("username", userByPhone.getUsername());
        String token = JwtUtils.createToken(map);
        cacheService.delete("LOGIN_STATUS_"+phone);
        cacheService.add("LOGIN_STATUS_"+phone, token, EXPIRATION, TimeUnit.DAYS);
        return R.ok().message("登陆成功").data("token", token);
    }

    /**
     * 发送登录验证码
     * @param phone 手机号
     * @return json
     * @throws Exception 异常
     */
    @PostMapping("/login/send/sms")
    public R loginSendSms(String phone) throws Exception {
        if (phone == null) {
            return R.error().message("手机号不能为空");
        }
        if (!CheckPhoneHelper.validator(phone)) {
            return R.error().message("手机号格式不正确");
        }
        if (Boolean.TRUE.equals(cacheService.hasKey("LOGIN_SMS_" + phone))){
            return R.error().message("验证码已下发至手机，请勿重复发送");
        }
        String code=VerificationCodeUtil.generateVerificationCode(6);
        SendSmsUtil.sendSms(code,phone);
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        cacheService.add("LOGIN_SMS_"+phone,mapper.writeValueAsString(map),EXPIRATION, TimeUnit.MINUTES);
        return R.ok().message("验证码发送成功");
    }

    /**
     * 通过验证码登录
     * @param phone 手机号
     * @param code 验证码
     * @return 返回json
     */
    @PostMapping("/login/by/sms")
    public R loginBySms(String phone, String code) throws JsonProcessingException {
        if (phone == null) {
            return R.error().message("手机号不能为空");
        }
        if (!CheckPhoneHelper.validator(phone)) {
            return R.error().message("手机号格式不正确");
        }
        //        检查数据库是否存在
        UserDto userByPhone = usersService.findUserByPhone(phone);
//        如果用户不存在
        if (userByPhone == null) {
            Users user = new Users();
            String password = KaisaUtil.decryptKaiser("123456", 13);
            String username = maskPhoneNumber(phone);
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            user.setUsername(username);
            user.setPassword(password);
            user.setCreateTime(createTime);
            user.setUpdateTime(createTime);
            user.setPhone(phone);
            usersService.addUser(user);
        }
        if (!Boolean.TRUE.equals(cacheService.hasKey("LOGIN_SMS_" + phone))){
            return R.error().message("请重新发送验证码");
        }
        String redisSms = cacheService.get("LOGIN_SMS_" + phone);
        Map<String, String> map = mapper.readValue(redisSms, Map.class);
        String storedCode = map.get("code");
        if (!code.equals(storedCode)) {
            return R.error().message("验证码错误");
        }
        cacheService.delete("LOGIN_SMS_" + phone);
        return R.ok().message("登陆成功");
    }

}
