package com.iisquare.sjt.cms.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String serial; // 账号（唯一约束）
    @Column
    private String name; // 昵称（唯一约束）
    @Column
    private String password;
    @Column
    private Integer salt; // 密码盐
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
    private String createdIp;
    @Column
    private Long updatedTime;
    @Column
    private Integer updatedUid;
    @Column
    private Long loginedTime; // 最后登录时间
    @Column
    private String loginedIp; // 最后登录IP
    @Column
    private Long lockedTime;

}
