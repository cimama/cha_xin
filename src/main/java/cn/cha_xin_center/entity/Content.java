package cn.cha_xin_center.entity;

import lombok.*;

@Data
public class Content {
    /**
    编号
     */
    private Integer id;
    /**
     项目名称
     */
    private String projectName;
    /**
     项目名称英文
     */
    private String projectNameEn;
    /**
     立项查新
     */
    private String targetsProject;
    /**
     成果查新
     */
    private String targetsResult;
    /**
     其他目的
     */
    private String targetsOther;
    /**
     查新范围
     */
    private String scope;
    /**
     查新点
     */
    private String noveltyPoint;
    /**
     查新科学技术要点
     */
    private String essential;
    /**
     参考检索词及其解释
     */
    private String noveltyTerm;
    /**
     知识论文
     */
    private String knowledgePaper;
    /**
     参考文献
     */
    private String referenceDoc;
    /**
     附件
     */
    private String file;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     随机时间戳
     */
    private Long randomId;
}
