package ua.khpi.voitenko.riskassessment.service;

import ua.khpi.voitenko.riskassessment.model.AssessmentLimit;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface AssessmentLimitService {

    List<AssessmentLimit> findAllAssessmentLimitsByUserId(User user);

    void saveAssessmentLimit(AssessmentLimit assessmentLimit);
}
