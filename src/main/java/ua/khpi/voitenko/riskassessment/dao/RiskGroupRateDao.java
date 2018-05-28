package ua.khpi.voitenko.riskassessment.dao;

import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;

import java.util.List;

public interface RiskGroupRateDao extends CommonDao<RiskGroupRate> {
    List<RiskGroupRate> findAllByUserId(int userId);
}
