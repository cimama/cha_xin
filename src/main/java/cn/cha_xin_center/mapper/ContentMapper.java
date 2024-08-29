package cn.cha_xin_center.mapper;

import cn.cha_xin_center.entity.Content;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper {
    int addContent(Content content);
    Content getContentByRandomId(int randomId);
}
