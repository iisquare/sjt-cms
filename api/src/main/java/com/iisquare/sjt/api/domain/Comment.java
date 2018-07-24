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
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String content;
    @Column
    private Integer categoryId;
    @Transient
    private String categoryIdName;
    @Column
    private Integer articleId;
    @Transient
    private String articleIdName;
    @Column
    private Integer parentId; // 父级评论主键
    @Column
    private Integer topId; // 一级评论主键
    @Transient
    private List<Comment> children;
    @Transient
    private String toUidName;
    @Column
    private Integer sort;
    @Column
    private Integer status;
    @Transient
    private String statusText;
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
