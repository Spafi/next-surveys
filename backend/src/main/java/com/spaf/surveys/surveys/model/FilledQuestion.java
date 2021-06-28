package com.spaf.surveys.surveys.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public @Data
class FilledQuestion {

    private String type;
    private String title;
    private List<Option> options;
}
