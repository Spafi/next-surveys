package com.spaf.surveys.surveys.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spaf.surveys.security.user.models.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public @Data
class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID responseId;

    private String value;

    @Transient
    private UUID questionId;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    private Question question;

    @JsonIgnore
    @ManyToOne
    private AppUser appUser;

    @Transient
    private UUID appUserId;

    @PostLoad
    private void postLoad() {
        appUserId = appUser.getId();
    }


}
