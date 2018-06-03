package ua.khpi.voitenko.riskassessment.dao;

import ua.khpi.voitenko.riskassessment.model.AssessmentLimit;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface AssessmentLimitDao extends CommonDao<AssessmentLimit> {
    List<AssessmentLimit> findAllByUserId(User user);
}
