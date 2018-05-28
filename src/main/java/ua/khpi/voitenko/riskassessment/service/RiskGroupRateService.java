package ua.khpi.voitenko.riskassessment.service;

import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;

import java.util.List;

public interface RiskGroupRateService {

    List<RiskGroupRate> findAllRiskGroupRatesByUserId(int userId);

}
