package com.iisquare.sjt.api.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title; // 标题
    @Column
    private Integer categoryId; // 栏目主键
    @Transient
    private String categoryIdName;
    @Column
    private String fromName; // 来源名称
    @Column
    private String fromUrl; // 来源地址
    @Column
    private String author; // 作者&责任编辑，默认为操作者
    @Column
    private String thumbUrl; // 缩略图地址
    @Column
    private String url; // 链接地址，留空默认为跳转到落地页
    @Column
    private String target; // 打开方式
    @Column
    private Integer commentStatus; // 是否允许评论
    @Column
    private Long sort; // 排序，默认为发布&创建时间
    @Column
    private Integer status;
    @Transient
    private String statusText;
    @Column
    private String description; // 摘要
    @Column
    private String content; // 正文
    @Column
    private Long publishTime; // 发布时间，默认为创建时间
    @Column
    private Long createdTime;
    @Column
    private Integer createdUid;
    @Transient
    private String createdUidName;
    @Column
    private Long updatedTime;
    @Column
    private Integer updatedUid;
    @Transient
    private String updatedUidName;

}
