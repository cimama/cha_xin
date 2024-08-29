package cn.cha_xin_center.service;

import cn.cha_xin_center.entity.Content;

public interface ContentService {

    int addContent(Content content);
    Content getContentByRandomId(int randomId);
}
