package com.spaf.surveys.surveys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spaf.surveys.security.user.models.AppUser;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public @Data
class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID surveyId;
    private String title;
    private String description;

    @OneToMany(mappedBy="survey", cascade=CascadeType.ALL)
    private List<Question> questions;

    @JsonIgnore
    @ManyToOne
    private AppUser appUser;

}
