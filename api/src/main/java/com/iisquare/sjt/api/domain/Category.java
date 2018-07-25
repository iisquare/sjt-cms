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
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer parentId;
    @Transient
    private String parentIdName;
    @Transient
    private List<Category> children;
    @Column
    private String url; // 链接地址，留空默认为跳转到列表页
    @Column
    private String target; // 打开方式
    @Column
    private Integer sort;
    @Column
    private Integer status;
    @Transient
    private String statusText;
    @Column
    private String keywords; // 关键词
    @Column
    private String description;
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
