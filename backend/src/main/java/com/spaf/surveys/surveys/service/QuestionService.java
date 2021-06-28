package com.spaf.surveys.surveys.service;


import com.spaf.surveys.surveys.model.Question;
import com.spaf.surveys.surveys.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question getQuestionById(UUID id) {
//        TODO: NULL CHECK
        return questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Question found with ID: "+ id.toString()));
    }






}
