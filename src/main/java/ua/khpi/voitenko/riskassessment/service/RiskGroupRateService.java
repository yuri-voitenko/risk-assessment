package ua.khpi.voitenko.riskassessment.service;

import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.model.User;

import java.util.List;

public interface RiskGroupRateService {

    List<RiskGroupRate> findAllRiskGroupRatesByUserId(User user);

    void saveRiskGroupRate(RiskGroupRate riskGroupRate);

    List<RiskGroupRate> findAllInitialSettings();
}
