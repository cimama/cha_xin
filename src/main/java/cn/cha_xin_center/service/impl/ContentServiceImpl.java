package cn.cha_xin_center.service.impl;

import cn.cha_xin_center.entity.Content;
import cn.cha_xin_center.mapper.ContentMapper;
import cn.cha_xin_center.service.ContentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentMapper contentMapper;

    @Override
    public int addContent(Content content) {
        return contentMapper.addContent(content);
    }

    @Override
    public Content getContentByRandomId(int randomId) {
        return contentMapper.getContentByRandomId(randomId);
    }
}
