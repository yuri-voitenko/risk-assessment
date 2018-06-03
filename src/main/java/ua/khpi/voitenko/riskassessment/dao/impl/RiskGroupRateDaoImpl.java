package ua.khpi.voitenko.riskassessment.dao.impl;

import org.springframework.stereotype.Repository;
import ua.khpi.voitenko.riskassessment.dao.RiskGroupRateDao;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

@Repository
public class RiskGroupRateDaoImpl extends AbstractDao<RiskGroupRate> implements RiskGroupRateDao {
    @Override
    public List<RiskGroupRate> findAllByUserId(User user) {
        return getEntityManager()
                .createQuery("SELECT rgr from RiskGroupRate rgr WHERE rgr.owner = :user", RiskGroupRate.class).
                        setParameter("user", user).getResultList();
    }
}
