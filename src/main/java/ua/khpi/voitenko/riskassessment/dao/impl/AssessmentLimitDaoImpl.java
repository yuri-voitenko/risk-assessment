package ua.khpi.voitenko.riskassessment.dao.impl;

import org.springframework.stereotype.Repository;
import ua.khpi.voitenko.riskassessment.dao.AssessmentLimitDao;
import ua.khpi.voitenko.riskassessment.model.AssessmentLimit;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

@Repository
public class AssessmentLimitDaoImpl extends AbstractDao<AssessmentLimit> implements AssessmentLimitDao {
    @Override
    public List<AssessmentLimit> findAllByUserId(User user) {
        return getEntityManager()
                .createQuery("SELECT al from AssessmentLimit al WHERE al.owner = :user", AssessmentLimit.class).
                        setParameter("user", user).getResultList();
    }
}
