package ua.khpi.voitenko.riskassessment.dao;

import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface RiskGroupRateDao extends CommonDao<RiskGroupRate> {
    List<RiskGroupRate> findAllByUserId(User user);
}
