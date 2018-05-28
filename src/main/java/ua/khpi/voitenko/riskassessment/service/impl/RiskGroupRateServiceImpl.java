package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.RiskGroupRateDao;
import ua.khpi.voitenko.riskassessment.model.RiskGroupRate;
import ua.khpi.voitenko.riskassessment.service.RiskGroupRateService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiskGroupRateServiceImpl implements RiskGroupRateService {
    @Resource
    private RiskGroupRateDao riskGroupRateDao;

    @Override
    public List<RiskGroupRate> findAllRiskGroupRatesByUserId(int userId) {
        return riskGroupRateDao.findAllByUserId(userId);
    }
}
