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
public class Settings implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name; // 配置项名称
    @Column
    private String type; // 配置项类型
    @Column
    private String content; // 配置项内容
    @Column
    private Integer sort;
    @Column
    private Integer status;
    @Column
    private String description;
    @Column
    private Long updated_time;
    @Column
    private Integer updated_uid;

}