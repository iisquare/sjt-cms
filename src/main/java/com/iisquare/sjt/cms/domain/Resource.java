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
public class Resource implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer parent;
    @Column
    private String module;
    @Column
    private String controller;
    @Column
    private String action;
    @Column
    private Integer sort;
    @Column
    private Integer status;
    @Column
    private String description;
    @Column
    private Long created_time;
    @Column
    private Integer created_uid;
    @Column
    private Long updated_time;
    @Column
    private Integer updated_uid;

}
