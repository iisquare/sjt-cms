package com.iisquare.sjt.cms.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Relation.IdClass.class)
public class Relation implements Serializable {

    @Id
    private Integer aid;
    @Id
    private Integer bid;

    @Data
    public class IdClass implements Serializable {

        private Integer aid;
        private Integer bid;

    }

}
