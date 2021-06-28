package com.spaf.surveys.surveys.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public @Data
class FilledSurvey {

    private String title;
    private String description;
    private List<Question> questions;
}
