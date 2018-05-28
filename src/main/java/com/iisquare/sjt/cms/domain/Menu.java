package com.iisquare.sjt.cms.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
public class Menu implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer parent;
    @Column
    private String icon; // 图标
    @Column
    private String url;
    @Column
    private String target; // 打开方式
    @Column
    private Integer sort;
    @Column
    private Integer status;
    @Column
    private String description;
    @Column
    private Long createdTime;
    @Column
    private Integer createdUid;
    @Column
    private Long updatedTime;
    @Column
    private Integer updatedUid;

}
