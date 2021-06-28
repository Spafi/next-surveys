package com.spaf.surveys.surveys.controller;

import com.spaf.surveys.surveys.model.ResponseBody;
import com.spaf.surveys.surveys.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/respond")
@AllArgsConstructor
@CrossOrigin
public class ResponseController {

    @Autowired
    private final ResponseService responseService;

    @PostMapping(value = "/{surveyId}")
    public void save(@PathVariable UUID surveyId, @RequestBody ResponseBody responseBody) {
        responseService.save(surveyId, responseBody);
    }

}
