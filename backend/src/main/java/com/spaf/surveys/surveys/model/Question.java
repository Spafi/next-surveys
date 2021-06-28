package com.spaf.surveys.surveys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public @Data
class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "pg-uuid")
    private UUID questionId;

    private String type;

    private String title;

    private Boolean required;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Option> options;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Survey survey;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
    private List<Response> responses;

    @Transient
    private List<Response> questionResponses;

    private String imageName;
}
