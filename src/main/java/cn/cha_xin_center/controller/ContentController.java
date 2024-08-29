package cn.cha_xin_center.controller;

import cn.cha_xin_center.common.R;
import cn.cha_xin_center.entity.Content;
import cn.cha_xin_center.entity.dto.UserDto;
import cn.cha_xin_center.service.ContentService;
import cn.cha_xin_center.service.auth.UserAuth;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/user")
public class ContentController {

    @Resource
    private ContentService contentService;

    @Resource
    private UserAuth userAuth;

    @PostMapping("/add/content")
    public R addContent(Content content) {
        System.out.println(content);
        UserDto user = userAuth.getUser();
        if (user == null) {
            return R.error().message("请先登录");
        }
        Integer userId = user.getId();
        System.out.println("userId = " + userId);
        String projectName = content.getProjectName();
        if (projectName == null) {
            return R.error().message("请输入项目名称");
        }
        content.setUserId(userId);
        // 获取当前时间戳
        Instant instant = Instant.now();
        long timestamp = instant.toEpochMilli();
        content.setRandomId(timestamp);
        int i = contentService.addContent(content);
        if (i > 0) {
            return R.ok().data("randomId",timestamp);
        }
        return R.error();
    }
//    通过时间戳查询id
    @GetMapping("/get/content/{randomId}")
    public R getContent(@PathVariable String randomId) {
        Content content = contentService.getContentByRandomId(Integer.parseInt(randomId));
        if (content == null) {
            return R.error();
        }
        Integer id = content.getId();
        return R.ok().data("id", id);
    }

}
