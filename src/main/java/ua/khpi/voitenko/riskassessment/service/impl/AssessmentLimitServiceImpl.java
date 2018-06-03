package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.AssessmentLimitDao;
import ua.khpi.voitenko.riskassessment.dao.UserDao;
import ua.khpi.voitenko.riskassessment.model.AssessmentLimit;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.AssessmentLimitService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssessmentLimitServiceImpl implements AssessmentLimitService {
    @Resource
    private AssessmentLimitDao assessmentLimitDao;
    @Resource
    private UserDao userDao;

    @Override
    public List<AssessmentLimit> findAllAssessmentLimitsByUserId(User user) {
        return assessmentLimitDao.findAllByUserId(user);
    }

    @Override
    public void saveAssessmentLimit(AssessmentLimit assessmentLimit) {
        assessmentLimitDao.insertOrUpdate(assessmentLimit);
    }

    @Override
    public List<AssessmentLimit> findAllInitialSettings() {
        return assessmentLimitDao.findAllByUserId(userDao.getAdminUser());
    }
}
