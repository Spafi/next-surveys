package com.spaf.surveys.surveys.controller;

import com.spaf.surveys.surveys.model.FilledSurvey;
import com.spaf.surveys.surveys.model.Survey;
import com.spaf.surveys.surveys.service.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/survey")
@AllArgsConstructor
@CrossOrigin
public class SurveyController {

    @Autowired
    private final SurveyService surveyService;

    @PostMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public UUID save(
            @PathVariable UUID userId,
            @RequestPart("survey") Survey survey,
            @Nullable @RequestPart("images") List<MultipartFile> images)
            throws IOException {
        return surveyService.save(userId, survey, images);
    }

    @GetMapping(value = "/{surveyId}")
    public Survey getSurveyById(@PathVariable UUID surveyId) {
        return surveyService.getSurveyById(surveyId);
    }

    @GetMapping(value = "/user/{userId}")
    public List<Survey> getSurveysByUserId(@PathVariable UUID userId) {
        return surveyService.getSurveyByUserId(userId);
    }

    @GetMapping(value = "/{surveyId}/responses")
    public FilledSurvey getSurveyWithResponses(@PathVariable UUID surveyId) {
        return surveyService.getFilledSurvey(surveyId);
    }

    @DeleteMapping(value = "/{surveyId}")
    public void deleteSurvey(@PathVariable UUID surveyId) {
        surveyService.deleteSurveyById(surveyId);
    }
}
