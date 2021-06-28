package com.spaf.surveys.surveys.repository;


import com.spaf.surveys.surveys.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, UUID> {
    List<Survey> findByAppUserId(UUID userId);
}
