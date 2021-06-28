package com.spaf.surveys.surveys.service;


import com.spaf.surveys.security.user.models.AppUser;
import com.spaf.surveys.security.user.services.AppUserService;
import com.spaf.surveys.surveys.model.Response;
import com.spaf.surveys.surveys.model.ResponseBody;
import com.spaf.surveys.surveys.model.Survey;
import com.spaf.surveys.surveys.repository.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResponseService {

    @Autowired
    private AppUserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionService questionService;


    public void save(UUID surveyId, ResponseBody responseBody) {
        System.out.println("Survey ID" + surveyId);
        System.out.println("User ID: " + responseBody.getAppUserId());
        List<List<Response>> responseListe = responseBody.getResponses();
        responseListe.forEach(System.out::println);

        AppUser user = userService.findAppUserById(responseBody.getAppUserId());

        Survey survey = surveyService.getSurveyById(surveyId);


        List<List<Response>> responseList = responseBody.getResponses();

        responseList.forEach(responses -> {
            try {
            responses.forEach(response -> {
                response.setAppUser(user);
                response.setQuestion(questionService.getQuestionById(response.getQuestionId()));
                responseRepository.save(response);
            } );}
            catch (NullPointerException ignored) {}

        });

    }


}
