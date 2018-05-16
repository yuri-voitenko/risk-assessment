package ua.khpi.voitenko.riskassessment.service.impl;

import org.springframework.stereotype.Service;
import ua.khpi.voitenko.riskassessment.dao.RiskDao;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiskServiceImpl implements RiskService {
    @Resource
    private RiskDao riskDao;

    @Override
    public List<Risk> findAllRisks() {
        return riskDao.findAll();
    }
}
