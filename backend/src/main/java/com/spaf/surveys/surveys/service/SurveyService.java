package com.spaf.surveys.surveys.service;


import com.spaf.surveys.security.user.services.AppUserService;
import com.spaf.surveys.surveys.model.*;
import com.spaf.surveys.surveys.repository.OptionRepository;
import com.spaf.surveys.surveys.repository.QuestionRepository;
import com.spaf.surveys.surveys.repository.SurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@AllArgsConstructor
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AppUserService userService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;


    private List<Option> getQuestionOptions(Question question) {
        List<Option> options = new ArrayList<>(question.getOptions());
        for (Option option : options) option.setQuestion(question);
        return options;
    }

    public Survey getSurveyById(UUID surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(() -> new EntityNotFoundException("No Survey found with ID: " + surveyId.toString()));
    }

    public UUID save(UUID userId, Survey survey, List<MultipartFile> images) throws IOException {

        survey.setAppUser(userService.findAppUserById(userId));

        List<Question> questions = new ArrayList<>(survey.getQuestions());
        if (images != null) {
            images.forEach(image -> {
                try {
                    saveUploadedFile(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Question question : questions) {
            question.setSurvey(survey);
            try {
                optionRepository.saveAll(getQuestionOptions(question));
            } catch (NullPointerException ignored) {
            }
        }
        questionRepository.saveAll(questions);
        return surveyRepository.save(survey).getSurveyId();
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            byte[] bytes = file.getBytes();
            Path path = Paths.get("src\\main\\java\\com\\spaf\\jwt\\jwt101\\surveys\\images\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/v1/images/getImage/")
                    .path(fileName)
                    .toUriString();
            // return the download image url as a response entity
            return fileDownloadUri;
        }
        return null;
    }


    public List<Survey> getSurveyByUserId(UUID userId) {
        return surveyRepository.findByAppUserId(userId);
    }

    public FilledSurvey getFilledSurvey(UUID surveyId) {
        Survey survey = getSurveyById(surveyId);

        Set<Question> questionSet = new LinkedHashSet<>(survey.getQuestions());

        questionSet.forEach(question -> question.setQuestionResponses(question.getResponses()));

        survey.setQuestions(new ArrayList<>(questionSet));

        return new FilledSurvey(survey.getTitle(), survey.getDescription(), survey.getQuestions());
    }

    public void deleteSurveyById(UUID surveyId) {
        surveyRepository.deleteById(surveyId);
    }
}
