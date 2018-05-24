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
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String serial;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private Integer salt;
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
    private String created_ip;
    @Column
    private Long updated_time;
    @Column
    private Integer updated_uid;
    @Column
    private Long logined_time; // 最后登录时间
    @Column
    private String logined_ip; // 最后登录IP
    @Column
    private Long locked_time;

}
