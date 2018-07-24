package com.iisquare.sjt.api.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Upload implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name; // 标题
    @Column
    private String type; // 类型
    @Column
    private String contentType; // 类型
    @Column
    private String path; // 存储文件相对路径
    @Transient
    private String url; // 访问链接
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
